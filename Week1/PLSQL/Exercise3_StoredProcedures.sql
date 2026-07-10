-- ============================================================
-- Exercise 3: Stored Procedures
-- ============================================================
-- Demonstrates: CREATE OR REPLACE PROCEDURE, IN/OUT parameters,
--               DML inside procedures, business logic
-- ============================================================


-- ─────────────────────────────────────────
-- Scenario 1: ProcessMonthlyInterest
--             Apply 1% monthly interest to all Savings accounts.
-- ─────────────────────────────────────────
CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest AS
    v_count  NUMBER := 0;
    v_interest NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== ProcessMonthlyInterest: Applying 1% to all Savings Accounts ===');

    FOR rec IN (
        SELECT AccountID, Balance
        FROM   Accounts
        WHERE  AccountType = 'Savings'
        FOR UPDATE
    ) LOOP
        v_interest := ROUND(rec.Balance * 0.01, 2);  -- 1% interest

        UPDATE Accounts
        SET    Balance      = Balance + v_interest,
               LastModified = SYSDATE
        WHERE  AccountID    = rec.AccountID;

        DBMS_OUTPUT.PUT_LINE('Account ' || rec.AccountID
            || ': Balance $' || rec.Balance
            || ' + Interest $' || v_interest
            || ' = New Balance $' || (rec.Balance + v_interest));
        v_count := v_count + 1;
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Total accounts updated: ' || v_count);
END ProcessMonthlyInterest;
/


-- ─────────────────────────────────────────
-- Scenario 2: UpdateEmployeeBonus
--             Add a bonus percentage to all employees in a given department.
-- ─────────────────────────────────────────
CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus(
    p_department     IN VARCHAR2,
    p_bonus_percent  IN NUMBER
) AS
    v_count NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== UpdateEmployeeBonus: ' || p_bonus_percent
        || '% bonus for Department: ' || p_department || ' ===');

    FOR rec IN (
        SELECT EmployeeID, Name, Salary
        FROM   Employees
        WHERE  Department = p_department
        FOR UPDATE
    ) LOOP
        DECLARE
            v_bonus     NUMBER := ROUND(rec.Salary * p_bonus_percent / 100, 2);
            v_new_salary NUMBER := rec.Salary + v_bonus;
        BEGIN
            UPDATE Employees
            SET    Salary = v_new_salary
            WHERE  EmployeeID = rec.EmployeeID;

            DBMS_OUTPUT.PUT_LINE('  ' || rec.Name
                || ': Old Salary $' || rec.Salary
                || ' + Bonus $' || v_bonus
                || ' = New Salary $' || v_new_salary);
            v_count := v_count + 1;
        END;
    END LOOP;

    IF v_count = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No employees found in department: ' || p_department);
    ELSE
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Bonus applied to ' || v_count || ' employee(s).');
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR in UpdateEmployeeBonus: ' || SQLERRM);
END UpdateEmployeeBonus;
/


-- ─────────────────────────────────────────
-- Scenario 3: TransferFunds
--             Transfer a specified amount between two accounts,
--             checking for sufficient balance first.
-- ─────────────────────────────────────────
CREATE OR REPLACE PROCEDURE TransferFunds(
    p_from_account_id IN NUMBER,
    p_to_account_id   IN NUMBER,
    p_amount          IN NUMBER
) AS
    v_from_balance NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== TransferFunds: Account ' || p_from_account_id
        || ' → Account ' || p_to_account_id
        || ' | Amount: $' || p_amount || ' ===');

    -- Validate amount
    IF p_amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20010, 'Transfer amount must be greater than zero.');
    END IF;

    -- Lock source account row and get balance
    SELECT Balance INTO v_from_balance
    FROM   Accounts
    WHERE  AccountID = p_from_account_id
    FOR UPDATE;

    -- Check sufficient balance
    IF v_from_balance < p_amount THEN
        RAISE_APPLICATION_ERROR(-20011,
            'Insufficient balance in account ' || p_from_account_id
            || '. Available: $' || v_from_balance
            || ', Required: $' || p_amount);
    END IF;

    -- Debit source account
    UPDATE Accounts
    SET    Balance = Balance - p_amount, LastModified = SYSDATE
    WHERE  AccountID = p_from_account_id;

    -- Credit destination account
    UPDATE Accounts
    SET    Balance = Balance + p_amount, LastModified = SYSDATE
    WHERE  AccountID = p_to_account_id;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('SUCCESS: $' || p_amount || ' transferred successfully.');
    DBMS_OUTPUT.PUT_LINE('Account ' || p_from_account_id || ' new balance: $' || (v_from_balance - p_amount));

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: One or both account IDs not found.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR in TransferFunds: ' || SQLERRM);
END TransferFunds;
/


-- ─────────────────────────────────────────
-- Test Calls for Exercise 3
-- ─────────────────────────────────────────
BEGIN DBMS_OUTPUT.PUT_LINE('=== Exercise 3: Stored Procedure Tests ==='); END;
/

BEGIN ProcessMonthlyInterest;        END;/       -- Apply 1% to all Savings accounts
BEGIN UpdateEmployeeBonus('IT', 15); END;/       -- 15% bonus to IT department
BEGIN UpdateEmployeeBonus('HR', 10); END;/       -- 10% bonus to HR department
BEGIN TransferFunds(1, 2, 200);      END;/       -- Valid transfer $200
BEGIN TransferFunds(2, 1, 50000);    END;/       -- Should fail: insufficient balance

-- Verify results
SELECT AccountID, AccountType, Balance FROM Accounts;
SELECT EmployeeID, Name, Salary, Department FROM Employees;
