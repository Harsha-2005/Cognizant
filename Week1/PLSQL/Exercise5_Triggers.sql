-- ============================================================
-- Exercise 5: Triggers
-- ============================================================
-- Demonstrates: CREATE OR REPLACE TRIGGER, BEFORE/AFTER DML,
--               :NEW / :OLD pseudo-records, AuditLog, business rules
-- ============================================================


-- ─────────────────────────────────────────
-- Scenario 1: UpdateCustomerLastModified
--             Automatically update the LastModified column
--             whenever a customer record is updated.
-- ─────────────────────────────────────────
CREATE OR REPLACE TRIGGER UpdateCustomerLastModified
    BEFORE UPDATE ON Customers
    FOR EACH ROW
BEGIN
    -- Set LastModified to current timestamp on every UPDATE
    :NEW.LastModified := SYSDATE;

    DBMS_OUTPUT.PUT_LINE('[Trigger] Customer "' || :OLD.Name
        || '" LastModified set to: ' || TO_CHAR(SYSDATE, 'DD-MON-YYYY HH24:MI:SS'));
END UpdateCustomerLastModified;
/


-- ─────────────────────────────────────────
-- Scenario 2: LogTransaction
--             Insert an audit record into AuditLog whenever
--             a new transaction is added to the Transactions table.
-- ─────────────────────────────────────────
CREATE OR REPLACE TRIGGER LogTransaction
    AFTER INSERT ON Transactions
    FOR EACH ROW
BEGIN
    INSERT INTO AuditLog (TransactionID, AccountID, TransactionDate, Amount, TransactionType, AuditTimestamp)
    VALUES (:NEW.TransactionID, :NEW.AccountID, :NEW.TransactionDate,
            :NEW.Amount, :NEW.TransactionType, SYSDATE);

    DBMS_OUTPUT.PUT_LINE('[Trigger] AuditLog: Transaction ID ' || :NEW.TransactionID
        || ' (' || :NEW.TransactionType || ', $' || :NEW.Amount
        || ') on Account ' || :NEW.AccountID || ' logged.');
END LogTransaction;
/


-- ─────────────────────────────────────────
-- Scenario 3: CheckTransactionRules
--             Enforce business rules BEFORE inserting a transaction:
--             - Withdrawals must not exceed the account balance.
--             - Deposit amounts must be positive.
--             Raises an application error and prevents the INSERT on violation.
-- ─────────────────────────────────────────
CREATE OR REPLACE TRIGGER CheckTransactionRules
    BEFORE INSERT ON Transactions
    FOR EACH ROW
DECLARE
    v_balance NUMBER;
BEGIN
    -- Rule 1: Amount must always be positive
    IF :NEW.Amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20030,
            'Transaction amount must be positive. Provided: ' || :NEW.Amount);
    END IF;

    -- Rule 2: Withdrawal cannot exceed current account balance
    IF UPPER(:NEW.TransactionType) = 'WITHDRAWAL' THEN
        SELECT Balance INTO v_balance
        FROM   Accounts
        WHERE  AccountID = :NEW.AccountID;

        IF :NEW.Amount > v_balance THEN
            RAISE_APPLICATION_ERROR(-20031,
                'Withdrawal of $' || :NEW.Amount
                || ' exceeds account balance of $' || v_balance
                || ' for Account ' || :NEW.AccountID);
        END IF;

        -- Deduct balance on valid withdrawal
        UPDATE Accounts
        SET    Balance      = Balance - :NEW.Amount,
               LastModified = SYSDATE
        WHERE  AccountID    = :NEW.AccountID;

        DBMS_OUTPUT.PUT_LINE('[Trigger] Withdrawal of $' || :NEW.Amount
            || ' approved. Account ' || :NEW.AccountID
            || ' new balance: $' || (v_balance - :NEW.Amount));

    ELSIF UPPER(:NEW.TransactionType) = 'DEPOSIT' THEN
        -- Credit account on deposit
        UPDATE Accounts
        SET    Balance      = Balance + :NEW.Amount,
               LastModified = SYSDATE
        WHERE  AccountID    = :NEW.AccountID;

        DBMS_OUTPUT.PUT_LINE('[Trigger] Deposit of $' || :NEW.Amount
            || ' approved for Account ' || :NEW.AccountID);
    END IF;
END CheckTransactionRules;
/


-- ─────────────────────────────────────────
-- Test Calls for Exercise 5
-- ─────────────────────────────────────────
BEGIN DBMS_OUTPUT.PUT_LINE('=== Exercise 5: Trigger Tests ==='); END;
/

-- Test Trigger 1: Update a customer — LastModified should auto-update
UPDATE Customers SET Balance = 13000 WHERE CustomerID = 1;
SELECT CustomerID, Name, Balance, TO_CHAR(LastModified, 'DD-MON-YYYY HH24:MI:SS') AS LastModified
FROM   Customers WHERE CustomerID = 1;

-- Test Trigger 2 & 3: Insert valid Deposit — should log to AuditLog
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (10, 1, SYSDATE, 500, 'Deposit');
COMMIT;

-- Insert valid Withdrawal (within balance)
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (11, 1, SYSDATE, 200, 'Withdrawal');
COMMIT;

-- Test Trigger 3: Attempt invalid Withdrawal (exceeds balance) — should FAIL
BEGIN
    INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
    VALUES (12, 2, SYSDATE, 99999, 'Withdrawal');
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Expected Error Caught: ' || SQLERRM);
END;
/

-- Verify AuditLog
SELECT * FROM AuditLog ORDER BY AuditTimestamp;
