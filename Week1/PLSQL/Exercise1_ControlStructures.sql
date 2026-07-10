-- ============================================================
-- Exercise 1: Control Structures
-- ============================================================
-- Demonstrates: IF-ELSIF-ELSE, FOR loop, WHILE / LOOP, Cursors
-- ============================================================

-- ─────────────────────────────────────────
-- Scenario 1: Apply 1% discount to loan interest rates
--             for customers above 60 years old.
-- ─────────────────────────────────────────
DECLARE
    v_age       NUMBER;
    v_customer  VARCHAR2(100);
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Scenario 1: Loan Interest Rate Discount (Age > 60) ===');

    FOR rec IN (
        SELECT c.CustomerID,
               c.Name,
               TRUNC(MONTHS_BETWEEN(SYSDATE, c.DOB) / 12) AS Age,
               l.LoanID,
               l.InterestRate
        FROM   Customers c
        JOIN   Loans     l ON c.CustomerID = l.CustomerID
    ) LOOP
        IF rec.Age > 60 THEN
            -- Apply 1% discount to interest rate
            UPDATE Loans
            SET    InterestRate = InterestRate - 1
            WHERE  LoanID       = rec.LoanID
              AND  InterestRate > 1;  -- Ensure rate doesn't go negative

            DBMS_OUTPUT.PUT_LINE('Customer: ' || rec.Name
                || ' (Age: ' || rec.Age || ')'
                || ' | Loan ID: ' || rec.LoanID
                || ' | Old Rate: ' || rec.InterestRate || '%'
                || ' | New Rate: ' || (rec.InterestRate - 1) || '%');
        ELSE
            DBMS_OUTPUT.PUT_LINE('Customer: ' || rec.Name
                || ' (Age: ' || rec.Age || ') — No discount applied.');
        END IF;
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Done.' || CHR(10));
END;
/


-- ─────────────────────────────────────────
-- Scenario 2: Set IsVIP = 'TRUE' for customers
--             with balance over $10,000.
-- ─────────────────────────────────────────
DECLARE
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Scenario 2: Promote Customers to VIP Status ===');

    FOR rec IN (
        SELECT CustomerID, Name, Balance
        FROM   Customers
    ) LOOP
        IF rec.Balance > 10000 THEN
            UPDATE Customers
            SET    IsVIP = 'TRUE'
            WHERE  CustomerID = rec.CustomerID;

            DBMS_OUTPUT.PUT_LINE('VIP Granted → ' || rec.Name
                || ' (Balance: $' || rec.Balance || ')');
        ELSE
            DBMS_OUTPUT.PUT_LINE('Standard    → ' || rec.Name
                || ' (Balance: $' || rec.Balance || ')');
        END IF;
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Done.' || CHR(10));
END;
/


-- ─────────────────────────────────────────
-- Scenario 3: Print reminders for loans due
--             within the next 30 days.
-- ─────────────────────────────────────────
DECLARE
    v_days_left NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== Scenario 3: Loan Due Reminders (Next 30 Days) ===');

    FOR rec IN (
        SELECT c.Name,
               c.CustomerID,
               l.LoanID,
               l.LoanAmount,
               l.EndDate,
               TRUNC(l.EndDate - SYSDATE) AS DaysLeft
        FROM   Customers c
        JOIN   Loans     l ON c.CustomerID = l.CustomerID
        WHERE  l.EndDate BETWEEN SYSDATE AND SYSDATE + 30
        ORDER BY l.EndDate
    ) LOOP
        DBMS_OUTPUT.PUT_LINE('REMINDER: Dear ' || rec.Name
            || ', your Loan (ID: ' || rec.LoanID
            || ', Amount: $' || rec.LoanAmount
            || ') is due in ' || rec.DaysLeft
            || ' day(s) on ' || TO_CHAR(rec.EndDate, 'DD-MON-YYYY') || '.');
    END LOOP;

    IF SQL%ROWCOUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No loans due within the next 30 days.');
    END IF;

    DBMS_OUTPUT.PUT_LINE('Done.');
END;
/
