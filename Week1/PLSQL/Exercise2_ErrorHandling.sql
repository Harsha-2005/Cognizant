-- ============================================================
-- Exercise 2: Error Handling
-- ============================================================
-- Demonstrates: EXCEPTION blocks, WHEN OTHERS, RAISE_APPLICATION_ERROR,
--               ROLLBACK, logging errors to ErrorLogs table
-- ============================================================


-- ─────────────────────────────────────────
-- Scenario 1: SafeTransferFunds
--             Transfer funds between accounts with full error handling.
--             Rolls back on any error and logs it to ErrorLogs.
-- ─────────────────────────────────────────
CREATE OR REPLACE PROCEDURE SafeTransferFunds(
    p_from_account_id IN NUMBER,
    p_to_account_id   IN NUMBER,
    p_amount          IN NUMBER
) AS
    v_from_balance NUMBER;
    v_from_exists  NUMBER;
    v_to_exists    NUMBER;
BEGIN
    -- Validate source account exists
    SELECT COUNT(*) INTO v_from_exists FROM Accounts WHERE AccountID = p_from_account_id;
    IF v_from_exists = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Source account ID ' || p_from_account_id || ' does not exist.');
    END IF;

    -- Validate destination account exists
    SELECT COUNT(*) INTO v_to_exists FROM Accounts WHERE AccountID = p_to_account_id;
    IF v_to_exists = 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Destination account ID ' || p_to_account_id || ' does not exist.');
    END IF;

    -- Validate transfer amount
    IF p_amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20003, 'Transfer amount must be positive. Provided: ' || p_amount);
    END IF;

    -- Check sufficient funds
    SELECT Balance INTO v_from_balance FROM Accounts WHERE AccountID = p_from_account_id FOR UPDATE;

    IF v_from_balance < p_amount THEN
        RAISE_APPLICATION_ERROR(-20004,
            'Insufficient funds. Account ' || p_from_account_id
            || ' has $' || v_from_balance || ', required $' || p_amount);
    END IF;

    -- Perform the transfer
    UPDATE Accounts SET Balance = Balance - p_amount, LastModified = SYSDATE
    WHERE  AccountID = p_from_account_id;

    UPDATE Accounts SET Balance = Balance + p_amount, LastModified = SYSDATE
    WHERE  AccountID = p_to_account_id;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('SUCCESS: Transferred $' || p_amount
        || ' from Account ' || p_from_account_id
        || ' to Account '   || p_to_account_id);

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        -- Log the error
        INSERT INTO ErrorLogs (Procedure, Message)
        VALUES ('SafeTransferFunds', SQLERRM);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR in SafeTransferFunds: ' || SQLERRM);
END SafeTransferFunds;
/


-- ─────────────────────────────────────────
-- Scenario 2: UpdateSalary
--             Increase an employee's salary by a percentage.
--             Handles NO_DATA_FOUND when employee doesn't exist.
-- ─────────────────────────────────────────
CREATE OR REPLACE PROCEDURE UpdateSalary(
    p_employee_id     IN NUMBER,
    p_raise_percent   IN NUMBER
) AS
    v_current_salary NUMBER;
    v_new_salary     NUMBER;
    v_name           VARCHAR2(100);
BEGIN
    -- This will raise NO_DATA_FOUND if employee does not exist
    SELECT Salary, Name INTO v_current_salary, v_name
    FROM   Employees
    WHERE  EmployeeID = p_employee_id;

    v_new_salary := v_current_salary * (1 + p_raise_percent / 100);

    UPDATE Employees
    SET    Salary = v_new_salary
    WHERE  EmployeeID = p_employee_id;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('SUCCESS: ' || v_name
        || '''s salary updated from $' || v_current_salary
        || ' to $' || ROUND(v_new_salary, 2)
        || ' (' || p_raise_percent || '% raise).');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        INSERT INTO ErrorLogs (Procedure, Message)
        VALUES ('UpdateSalary', 'Employee ID ' || p_employee_id || ' not found.');
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: Employee ID ' || p_employee_id || ' does not exist.');

    WHEN OTHERS THEN
        ROLLBACK;
        INSERT INTO ErrorLogs (Procedure, Message)
        VALUES ('UpdateSalary', SQLERRM);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR in UpdateSalary: ' || SQLERRM);
END UpdateSalary;
/


-- ─────────────────────────────────────────
-- Scenario 3: AddNewCustomer
--             Insert a new customer; handle duplicate CustomerID gracefully.
-- ─────────────────────────────────────────
CREATE OR REPLACE PROCEDURE AddNewCustomer(
    p_customer_id IN NUMBER,
    p_name        IN VARCHAR2,
    p_dob         IN DATE,
    p_balance     IN NUMBER
) AS
BEGIN
    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (p_customer_id, p_name, p_dob, p_balance, SYSDATE);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('SUCCESS: Customer "' || p_name || '" (ID: ' || p_customer_id || ') added.');

EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        INSERT INTO ErrorLogs (Procedure, Message)
        VALUES ('AddNewCustomer',
                'Duplicate CustomerID: ' || p_customer_id || '. Customer "' || p_name || '" NOT inserted.');
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR: Customer ID ' || p_customer_id || ' already exists. Insertion prevented.');

    WHEN OTHERS THEN
        ROLLBACK;
        INSERT INTO ErrorLogs (Procedure, Message)
        VALUES ('AddNewCustomer', SQLERRM);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('ERROR in AddNewCustomer: ' || SQLERRM);
END AddNewCustomer;
/


-- ─────────────────────────────────────────
-- Test Calls for Exercise 2
-- ─────────────────────────────────────────
BEGIN DBMS_OUTPUT.PUT_LINE('=== Exercise 2: Error Handling Tests ==='); END;
/

-- Scenario 1 Tests
BEGIN SafeTransferFunds(1, 2, 500);  END;/  -- Valid transfer
BEGIN SafeTransferFunds(1, 2, 99999); END;/ -- Insufficient funds (should error)
BEGIN SafeTransferFunds(99, 2, 100);  END;/ -- Non-existent account (should error)

-- Scenario 2 Tests
BEGIN UpdateSalary(1, 10);  END;/   -- Valid: 10% raise for Employee 1
BEGIN UpdateSalary(99, 15); END;/   -- Invalid: Employee 99 doesn't exist

-- Scenario 3 Tests
BEGIN AddNewCustomer(5, 'Carol New', TO_DATE('2000-01-01','YYYY-MM-DD'), 500); END;/  -- Valid
BEGIN AddNewCustomer(1, 'Duplicate', TO_DATE('1990-01-01','YYYY-MM-DD'), 100); END;/ -- Duplicate ID

-- View error log
SELECT * FROM ErrorLogs ORDER BY LogTime DESC;
