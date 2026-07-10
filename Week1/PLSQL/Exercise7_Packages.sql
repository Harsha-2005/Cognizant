-- ============================================================
-- Exercise 7: Packages
-- ============================================================
-- Demonstrates: CREATE PACKAGE (specification) and
--               CREATE PACKAGE BODY (implementation),
--               grouping related procedures and functions
-- ============================================================


-- ─────────────────────────────────────────
-- Scenario 1: CustomerManagement Package
--             Groups: AddCustomer, UpdateCustomerDetails, GetCustomerBalance
-- ─────────────────────────────────────────

-- Package Specification (Public API)
CREATE OR REPLACE PACKAGE CustomerManagement AS

    -- Add a new customer to the Customers table
    PROCEDURE AddCustomer(
        p_customer_id IN NUMBER,
        p_name        IN VARCHAR2,
        p_dob         IN DATE,
        p_balance     IN NUMBER
    );

    -- Update an existing customer's name and balance
    PROCEDURE UpdateCustomerDetails(
        p_customer_id IN NUMBER,
        p_name        IN VARCHAR2,
        p_balance     IN NUMBER
    );

    -- Return the current balance of a customer
    FUNCTION GetCustomerBalance(
        p_customer_id IN NUMBER
    ) RETURN NUMBER;

END CustomerManagement;
/

-- Package Body (Implementation)
CREATE OR REPLACE PACKAGE BODY CustomerManagement AS

    PROCEDURE AddCustomer(
        p_customer_id IN NUMBER,
        p_name        IN VARCHAR2,
        p_dob         IN DATE,
        p_balance     IN NUMBER
    ) AS
    BEGIN
        INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
        VALUES (p_customer_id, p_name, p_dob, p_balance, SYSDATE);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('[CustomerManagement] Added customer: ' || p_name || ' (ID: ' || p_customer_id || ')');
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('[CustomerManagement] ERROR: Customer ID ' || p_customer_id || ' already exists.');
    END AddCustomer;


    PROCEDURE UpdateCustomerDetails(
        p_customer_id IN NUMBER,
        p_name        IN VARCHAR2,
        p_balance     IN NUMBER
    ) AS
        v_count NUMBER;
    BEGIN
        SELECT COUNT(*) INTO v_count FROM Customers WHERE CustomerID = p_customer_id;
        IF v_count = 0 THEN
            DBMS_OUTPUT.PUT_LINE('[CustomerManagement] ERROR: Customer ID ' || p_customer_id || ' not found.');
            RETURN;
        END IF;

        UPDATE Customers
        SET    Name    = p_name,
               Balance = p_balance
        WHERE  CustomerID = p_customer_id;
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('[CustomerManagement] Updated Customer ID ' || p_customer_id || ' → Name: ' || p_name || ', Balance: $' || p_balance);
    END UpdateCustomerDetails;


    FUNCTION GetCustomerBalance(
        p_customer_id IN NUMBER
    ) RETURN NUMBER AS
        v_balance NUMBER;
    BEGIN
        SELECT Balance INTO v_balance FROM Customers WHERE CustomerID = p_customer_id;
        RETURN v_balance;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('[CustomerManagement] ERROR: Customer ID ' || p_customer_id || ' not found.');
            RETURN NULL;
    END GetCustomerBalance;

END CustomerManagement;
/


-- ─────────────────────────────────────────
-- Scenario 2: EmployeeManagement Package
--             Groups: HireEmployee, UpdateEmployeeDetails, CalculateAnnualSalary
-- ─────────────────────────────────────────

CREATE OR REPLACE PACKAGE EmployeeManagement AS

    -- Hire a new employee
    PROCEDURE HireEmployee(
        p_employee_id IN NUMBER,
        p_name        IN VARCHAR2,
        p_position    IN VARCHAR2,
        p_salary      IN NUMBER,
        p_department  IN VARCHAR2,
        p_hire_date   IN DATE
    );

    -- Update employee position and salary
    PROCEDURE UpdateEmployeeDetails(
        p_employee_id IN NUMBER,
        p_position    IN VARCHAR2,
        p_salary      IN NUMBER
    );

    -- Return annual salary (monthly salary × 12)
    FUNCTION CalculateAnnualSalary(
        p_employee_id IN NUMBER
    ) RETURN NUMBER;

END EmployeeManagement;
/

CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS

    PROCEDURE HireEmployee(
        p_employee_id IN NUMBER,
        p_name        IN VARCHAR2,
        p_position    IN VARCHAR2,
        p_salary      IN NUMBER,
        p_department  IN VARCHAR2,
        p_hire_date   IN DATE
    ) AS
    BEGIN
        INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
        VALUES (p_employee_id, p_name, p_position, p_salary, p_department, p_hire_date);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('[EmployeeManagement] Hired: ' || p_name || ' as ' || p_position || ' in ' || p_department);
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('[EmployeeManagement] ERROR: Employee ID ' || p_employee_id || ' already exists.');
    END HireEmployee;


    PROCEDURE UpdateEmployeeDetails(
        p_employee_id IN NUMBER,
        p_position    IN VARCHAR2,
        p_salary      IN NUMBER
    ) AS
        v_count NUMBER;
    BEGIN
        SELECT COUNT(*) INTO v_count FROM Employees WHERE EmployeeID = p_employee_id;
        IF v_count = 0 THEN
            DBMS_OUTPUT.PUT_LINE('[EmployeeManagement] ERROR: Employee ID ' || p_employee_id || ' not found.');
            RETURN;
        END IF;

        UPDATE Employees
        SET    Position = p_position,
               Salary   = p_salary
        WHERE  EmployeeID = p_employee_id;
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('[EmployeeManagement] Updated Employee ' || p_employee_id
            || ': Position=' || p_position || ', Salary=$' || p_salary);
    END UpdateEmployeeDetails;


    FUNCTION CalculateAnnualSalary(
        p_employee_id IN NUMBER
    ) RETURN NUMBER AS
        v_monthly_salary NUMBER;
    BEGIN
        SELECT Salary INTO v_monthly_salary FROM Employees WHERE EmployeeID = p_employee_id;
        RETURN v_monthly_salary * 12;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('[EmployeeManagement] ERROR: Employee ID ' || p_employee_id || ' not found.');
            RETURN NULL;
    END CalculateAnnualSalary;

END EmployeeManagement;
/


-- ─────────────────────────────────────────
-- Scenario 3: AccountOperations Package
--             Groups: OpenAccount, CloseAccount, GetTotalBalance
-- ─────────────────────────────────────────

CREATE OR REPLACE PACKAGE AccountOperations AS

    -- Open a new account for a customer
    PROCEDURE OpenAccount(
        p_account_id   IN NUMBER,
        p_customer_id  IN NUMBER,
        p_account_type IN VARCHAR2,
        p_initial_balance IN NUMBER
    );

    -- Close an account by its ID
    PROCEDURE CloseAccount(
        p_account_id IN NUMBER
    );

    -- Return the total balance across all accounts for a customer
    FUNCTION GetTotalBalance(
        p_customer_id IN NUMBER
    ) RETURN NUMBER;

END AccountOperations;
/

CREATE OR REPLACE PACKAGE BODY AccountOperations AS

    PROCEDURE OpenAccount(
        p_account_id      IN NUMBER,
        p_customer_id     IN NUMBER,
        p_account_type    IN VARCHAR2,
        p_initial_balance IN NUMBER
    ) AS
    BEGIN
        INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
        VALUES (p_account_id, p_customer_id, p_account_type, p_initial_balance, SYSDATE);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('[AccountOperations] Opened ' || p_account_type
            || ' Account (ID: ' || p_account_id
            || ') for Customer ' || p_customer_id
            || ' with balance $' || p_initial_balance);
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('[AccountOperations] ERROR: Account ID ' || p_account_id || ' already exists.');
    END OpenAccount;


    PROCEDURE CloseAccount(
        p_account_id IN NUMBER
    ) AS
        v_count NUMBER;
    BEGIN
        SELECT COUNT(*) INTO v_count FROM Accounts WHERE AccountID = p_account_id;
        IF v_count = 0 THEN
            DBMS_OUTPUT.PUT_LINE('[AccountOperations] ERROR: Account ID ' || p_account_id || ' not found.');
            RETURN;
        END IF;

        DELETE FROM Transactions WHERE AccountID = p_account_id;
        DELETE FROM Accounts     WHERE AccountID = p_account_id;
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('[AccountOperations] Account ' || p_account_id || ' closed and all related transactions removed.');
    END CloseAccount;


    FUNCTION GetTotalBalance(
        p_customer_id IN NUMBER
    ) RETURN NUMBER AS
        v_total NUMBER;
    BEGIN
        SELECT NVL(SUM(Balance), 0)
        INTO   v_total
        FROM   Accounts
        WHERE  CustomerID = p_customer_id;
        RETURN v_total;
    END GetTotalBalance;

END AccountOperations;
/


-- ─────────────────────────────────────────
-- Test Calls for Exercise 7
-- ─────────────────────────────────────────
BEGIN DBMS_OUTPUT.PUT_LINE('=== Exercise 7: Package Tests ==='); END;
/

-- CustomerManagement
BEGIN CustomerManagement.AddCustomer(6, 'New Customer', TO_DATE('1995-08-10','YYYY-MM-DD'), 2000); END;/
BEGIN CustomerManagement.UpdateCustomerDetails(6, 'New Customer Updated', 2500); END;/
SELECT CustomerManagement.GetCustomerBalance(1) AS Customer1Balance FROM DUAL;
SELECT CustomerManagement.GetCustomerBalance(6) AS Customer6Balance FROM DUAL;

-- EmployeeManagement
BEGIN EmployeeManagement.HireEmployee(4, 'Dave Green', 'Tester', 52000, 'QA', SYSDATE); END;/
BEGIN EmployeeManagement.UpdateEmployeeDetails(4, 'Senior Tester', 58000); END;/
SELECT EmployeeManagement.CalculateAnnualSalary(1) AS Employee1AnnualSalary FROM DUAL;
SELECT EmployeeManagement.CalculateAnnualSalary(4) AS Employee4AnnualSalary FROM DUAL;

-- AccountOperations
BEGIN AccountOperations.OpenAccount(10, 1, 'Checking', 3000); END;/
SELECT AccountOperations.GetTotalBalance(1) AS Customer1TotalBalance FROM DUAL;
BEGIN AccountOperations.CloseAccount(10); END;/
SELECT AccountOperations.GetTotalBalance(1) AS Customer1TotalBalanceAfterClose FROM DUAL;
