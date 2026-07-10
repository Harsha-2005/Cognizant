-- ============================================================
-- Exercise 6: Cursors
-- ============================================================
-- Demonstrates: Explicit cursors, CURSOR declaration, OPEN/FETCH/CLOSE,
--               cursor FOR loops, %ROWTYPE, %NOTFOUND
-- ============================================================


-- ─────────────────────────────────────────
-- Scenario 1: GenerateMonthlyStatements
--             Retrieve all transactions for the current month
--             and print a statement per customer.
-- ─────────────────────────────────────────
DECLARE
    -- Explicit cursor: transactions in the current calendar month
    CURSOR GenerateMonthlyStatements IS
        SELECT c.CustomerID,
               c.Name        AS CustomerName,
               t.TransactionID,
               t.TransactionDate,
               t.Amount,
               t.TransactionType
        FROM   Customers    c
        JOIN   Accounts     a ON c.CustomerID   = a.CustomerID
        JOIN   Transactions t ON a.AccountID    = t.AccountID
        WHERE  EXTRACT(MONTH FROM t.TransactionDate) = EXTRACT(MONTH FROM SYSDATE)
          AND  EXTRACT(YEAR  FROM t.TransactionDate) = EXTRACT(YEAR  FROM SYSDATE)
        ORDER BY c.CustomerID, t.TransactionDate;

    v_prev_customer NUMBER := -1;
    rec GenerateMonthlyStatements%ROWTYPE;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Scenario 1: Monthly Statements ('
        || TO_CHAR(SYSDATE, 'MONTH YYYY') || ') ===');

    OPEN GenerateMonthlyStatements;
    LOOP
        FETCH GenerateMonthlyStatements INTO rec;
        EXIT WHEN GenerateMonthlyStatements%NOTFOUND;

        -- Print customer header when customer changes
        IF rec.CustomerID <> v_prev_customer THEN
            IF v_prev_customer <> -1 THEN
                DBMS_OUTPUT.PUT_LINE('  ─────────────────────────────────');
            END IF;
            DBMS_OUTPUT.PUT_LINE(CHR(10) || 'Customer: ' || rec.CustomerName
                || ' (ID: ' || rec.CustomerID || ')');
            DBMS_OUTPUT.PUT_LINE('  Date            | Type       | Amount');
            DBMS_OUTPUT.PUT_LINE('  ─────────────────────────────────');
            v_prev_customer := rec.CustomerID;
        END IF;

        DBMS_OUTPUT.PUT_LINE('  '
            || TO_CHAR(rec.TransactionDate, 'DD-MON-YYYY') || ' | '
            || RPAD(rec.TransactionType, 10) || ' | $'
            || rec.Amount);
    END LOOP;

    CLOSE GenerateMonthlyStatements;

    IF v_prev_customer = -1 THEN
        DBMS_OUTPUT.PUT_LINE('No transactions found for this month.');
    END IF;

    DBMS_OUTPUT.PUT_LINE(CHR(10) || 'Done.' || CHR(10));
END;
/


-- ─────────────────────────────────────────
-- Scenario 2: ApplyAnnualFee
--             Deduct a $50 annual maintenance fee from all accounts.
-- ─────────────────────────────────────────
DECLARE
    -- Explicit cursor to fetch all accounts with their balances
    CURSOR ApplyAnnualFee IS
        SELECT AccountID, CustomerID, AccountType, Balance
        FROM   Accounts
        FOR UPDATE;                     -- Lock rows for update

    v_annual_fee   CONSTANT NUMBER := 50;
    v_count        NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Scenario 2: Apply Annual Maintenance Fee ($' || v_annual_fee || ') ===');

    FOR rec IN ApplyAnnualFee LOOP
        IF rec.Balance >= v_annual_fee THEN
            UPDATE Accounts
            SET    Balance      = Balance - v_annual_fee,
                   LastModified = SYSDATE
            WHERE  CURRENT OF ApplyAnnualFee;    -- Efficient positioned UPDATE

            DBMS_OUTPUT.PUT_LINE('Account ' || rec.AccountID
                || ' (' || rec.AccountType
                || '): $' || rec.Balance
                || ' - $' || v_annual_fee
                || ' = $' || (rec.Balance - v_annual_fee));
        ELSE
            DBMS_OUTPUT.PUT_LINE('Account ' || rec.AccountID
                || ': Insufficient balance to deduct annual fee (Balance: $' || rec.Balance || ')');
        END IF;
        v_count := v_count + 1;
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Annual fee processed for ' || v_count || ' account(s).' || CHR(10));
END;
/


-- ─────────────────────────────────────────
-- Scenario 3: UpdateLoanInterestRates
--             Update loan interest rates based on a new policy:
--             - LoanAmount > 10000 → new rate = old rate + 0.5%
--             - LoanAmount ≤ 10000 → new rate = old rate - 0.25% (incentive)
-- ─────────────────────────────────────────
DECLARE
    -- Explicit cursor to fetch all active loans
    CURSOR UpdateLoanInterestRates IS
        SELECT LoanID, CustomerID, LoanAmount, InterestRate
        FROM   Loans
        FOR UPDATE;

    v_new_rate NUMBER;
    v_count    NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Scenario 3: Update Loan Interest Rates (New Policy) ===');

    FOR rec IN UpdateLoanInterestRates LOOP
        -- Determine new rate based on loan amount policy
        IF rec.LoanAmount > 10000 THEN
            v_new_rate := rec.InterestRate + 0.5;
        ELSE
            v_new_rate := GREATEST(rec.InterestRate - 0.25, 0);  -- Never below 0%
        END IF;

        UPDATE Loans
        SET    InterestRate = v_new_rate
        WHERE  CURRENT OF UpdateLoanInterestRates;

        DBMS_OUTPUT.PUT_LINE('Loan ' || rec.LoanID
            || ' (Customer ' || rec.CustomerID
            || ', Amount $' || rec.LoanAmount
            || '): ' || rec.InterestRate || '% → ' || v_new_rate || '%');
        v_count := v_count + 1;
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Interest rates updated for ' || v_count || ' loan(s).');
END;
/


-- Verify results
SELECT LoanID, CustomerID, LoanAmount, InterestRate FROM Loans;
SELECT AccountID, AccountType, Balance FROM Accounts;
