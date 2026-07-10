-- ============================================================
-- Exercise 4: Functions
-- ============================================================
-- Demonstrates: CREATE OR REPLACE FUNCTION, RETURN types,
--               date arithmetic, financial calculations
-- ============================================================


-- ─────────────────────────────────────────
-- Scenario 1: CalculateAge
--             Returns the age in years given a date of birth.
-- ─────────────────────────────────────────
CREATE OR REPLACE FUNCTION CalculateAge(
    p_dob IN DATE
) RETURN NUMBER AS
    v_age NUMBER;
BEGIN
    -- MONTHS_BETWEEN gives fractional months; divide by 12 and floor for completed years
    v_age := FLOOR(MONTHS_BETWEEN(SYSDATE, p_dob) / 12);
    RETURN v_age;
END CalculateAge;
/


-- ─────────────────────────────────────────
-- Scenario 2: CalculateMonthlyInstallment
--             Computes EMI using standard loan amortization formula:
--             EMI = P × r × (1+r)^n / ((1+r)^n - 1)
--             where P = principal, r = monthly rate, n = total months
-- ─────────────────────────────────────────
CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment(
    p_loan_amount   IN NUMBER,
    p_annual_rate   IN NUMBER,   -- Annual interest rate in % (e.g., 8 for 8%)
    p_years         IN NUMBER    -- Loan duration in years
) RETURN NUMBER AS
    v_monthly_rate NUMBER;
    v_n_months     NUMBER;
    v_emi          NUMBER;
BEGIN
    -- Convert annual rate to monthly decimal
    v_monthly_rate := (p_annual_rate / 100) / 12;
    v_n_months     := p_years * 12;

    IF v_monthly_rate = 0 THEN
        -- Edge case: zero interest → simple division
        v_emi := p_loan_amount / v_n_months;
    ELSE
        -- Standard EMI formula
        v_emi := p_loan_amount
                 * v_monthly_rate
                 * POWER(1 + v_monthly_rate, v_n_months)
                 / (POWER(1 + v_monthly_rate, v_n_months) - 1);
    END IF;

    RETURN ROUND(v_emi, 2);
END CalculateMonthlyInstallment;
/


-- ─────────────────────────────────────────
-- Scenario 3: HasSufficientBalance
--             Returns 1 (TRUE) if the account has at least the required amount,
--             0 (FALSE) otherwise.
--             Note: Oracle PL/SQL functions cannot return BOOLEAN to SQL layer,
--                   so we use NUMBER (1 = TRUE, 0 = FALSE) for SQL compatibility.
-- ─────────────────────────────────────────
CREATE OR REPLACE FUNCTION HasSufficientBalance(
    p_account_id IN NUMBER,
    p_amount     IN NUMBER
) RETURN NUMBER AS      -- 1 = TRUE, 0 = FALSE
    v_balance    NUMBER;
    v_count      NUMBER;
BEGIN
    -- Check account exists
    SELECT COUNT(*) INTO v_count FROM Accounts WHERE AccountID = p_account_id;
    IF v_count = 0 THEN
        RAISE_APPLICATION_ERROR(-20020, 'Account ID ' || p_account_id || ' does not exist.');
    END IF;

    SELECT Balance INTO v_balance FROM Accounts WHERE AccountID = p_account_id;

    IF v_balance >= p_amount THEN
        RETURN 1;  -- Sufficient balance
    ELSE
        RETURN 0;  -- Insufficient balance
    END IF;
END HasSufficientBalance;
/


-- ─────────────────────────────────────────
-- Test Calls for Exercise 4
-- ─────────────────────────────────────────
BEGIN DBMS_OUTPUT.PUT_LINE('=== Exercise 4: Function Tests ==='); END;
/

-- Test CalculateAge
SELECT Name,
       TO_CHAR(DOB, 'DD-MON-YYYY') AS DOB,
       CalculateAge(DOB)            AS Age
FROM   Customers
ORDER BY CustomerID;

-- Test CalculateMonthlyInstallment
SELECT
    CalculateMonthlyInstallment(100000, 8, 10) AS EMI_100k_8pct_10yr,
    CalculateMonthlyInstallment(50000,  6, 5)  AS EMI_50k_6pct_5yr,
    CalculateMonthlyInstallment(200000, 9, 20) AS EMI_200k_9pct_20yr
FROM DUAL;

-- Test HasSufficientBalance
SELECT AccountID,
       Balance,
       HasSufficientBalance(AccountID, 500)   AS Has500,
       HasSufficientBalance(AccountID, 5000)  AS Has5000,
       HasSufficientBalance(AccountID, 20000) AS Has20000
FROM   Accounts
ORDER BY AccountID;

-- Using HasSufficientBalance in PL/SQL context
DECLARE
    v_result NUMBER;
BEGIN
    v_result := HasSufficientBalance(1, 1000);
    IF v_result = 1 THEN
        DBMS_OUTPUT.PUT_LINE('Account 1: Has sufficient balance for $1000 transaction.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Account 1: INSUFFICIENT balance for $1000 transaction.');
    END IF;
END;
/
