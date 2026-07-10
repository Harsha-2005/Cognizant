
CREATE TABLE Customers (
    CustomerID   NUMBER PRIMARY KEY,
    Name         VARCHAR2(100),
    DOB          DATE,
    Balance      NUMBER,
    LastModified DATE,
    IsVIP        VARCHAR2(5) DEFAULT 'FALSE'
);

-- ─────────────────────────────────────────
-- Table: Accounts
-- ─────────────────────────────────────────
CREATE TABLE Accounts (
    AccountID    NUMBER PRIMARY KEY,
    CustomerID   NUMBER,
    AccountType  VARCHAR2(20),
    Balance      NUMBER,
    LastModified DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

-- ─────────────────────────────────────────
-- Table: Transactions
-- ─────────────────────────────────────────
CREATE TABLE Transactions (
    TransactionID   NUMBER PRIMARY KEY,
    AccountID       NUMBER,
    TransactionDate DATE,
    Amount          NUMBER,
    TransactionType VARCHAR2(10),
    FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID)
);

-- ─────────────────────────────────────────
-- Table: Loans
-- ─────────────────────────────────────────
CREATE TABLE Loans (
    LoanID       NUMBER PRIMARY KEY,
    CustomerID   NUMBER,
    LoanAmount   NUMBER,
    InterestRate NUMBER,
    StartDate    DATE,
    EndDate      DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

-- ─────────────────────────────────────────
-- Table: Employees
-- ─────────────────────────────────────────
CREATE TABLE Employees (
    EmployeeID NUMBER PRIMARY KEY,
    Name       VARCHAR2(100),
    Position   VARCHAR2(50),
    Salary     NUMBER,
    Department VARCHAR2(50),
    HireDate   DATE
);

-- ─────────────────────────────────────────
-- Table: ErrorLogs  (used by error-handling exercises)
-- ─────────────────────────────────────────
CREATE TABLE ErrorLogs (
    LogID     NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    LogTime   DATE           DEFAULT SYSDATE,
    Procedure VARCHAR2(100),
    Message   VARCHAR2(500)
);

-- ─────────────────────────────────────────
-- Table: AuditLog  (used by trigger exercises)
-- ─────────────────────────────────────────
CREATE TABLE AuditLog (
    AuditID         NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    TransactionID   NUMBER,
    AccountID       NUMBER,
    TransactionDate DATE,
    Amount          NUMBER,
    TransactionType VARCHAR2(10),
    AuditTimestamp  DATE DEFAULT SYSDATE
);

-- ─────────────────────────────────────────
-- Sample Data
-- ─────────────────────────────────────────

-- Customers (mix of ages: some above 60, some below; some high-balance for VIP)
INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (1, 'John Doe',    TO_DATE('1955-05-15', 'YYYY-MM-DD'), 12000, SYSDATE);   -- Age > 60, Balance > 10000 → VIP

INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (2, 'Jane Smith',  TO_DATE('1990-07-20', 'YYYY-MM-DD'), 1500,  SYSDATE);  -- Age < 60, Balance < 10000

INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (3, 'Alice Brown', TO_DATE('1948-03-10', 'YYYY-MM-DD'), 9500,  SYSDATE);  -- Age > 60, Balance < 10000

INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
VALUES (4, 'Bob Wilson',  TO_DATE('1980-11-25', 'YYYY-MM-DD'), 15000, SYSDATE);  -- Age < 60, Balance > 10000 → VIP

-- Accounts
INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
VALUES (1, 1, 'Savings',  12000, SYSDATE);

INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
VALUES (2, 2, 'Checking',  1500, SYSDATE);

INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
VALUES (3, 3, 'Savings',   9500, SYSDATE);

INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
VALUES (4, 4, 'Savings',  15000, SYSDATE);

-- Transactions
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (1, 1, SYSDATE,                200, 'Deposit');

INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (2, 2, SYSDATE,                300, 'Withdrawal');

INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (3, 3, ADD_MONTHS(SYSDATE,-1), 500, 'Deposit');

-- Loans (one due within 30 days to demonstrate reminders)
INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
VALUES (1, 1, 5000, 5.0, ADD_MONTHS(SYSDATE,-60), SYSDATE + 10);  -- Due in 10 days

INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
VALUES (2, 2, 8000, 7.5, ADD_MONTHS(SYSDATE,-12), ADD_MONTHS(SYSDATE, 6));  -- Not due soon

INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
VALUES (3, 3, 3000, 6.0, ADD_MONTHS(SYSDATE,-24), SYSDATE + 25);  -- Due in 25 days

-- Employees
INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
VALUES (1, 'Alice Johnson', 'Manager',   70000, 'HR', TO_DATE('2015-06-15', 'YYYY-MM-DD'));

INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
VALUES (2, 'Bob Brown',     'Developer', 60000, 'IT', TO_DATE('2017-03-20', 'YYYY-MM-DD'));

INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
VALUES (3, 'Carol Davis',   'Analyst',   55000, 'IT', TO_DATE('2019-09-01', 'YYYY-MM-DD'));

COMMIT;

SELECT 'Schema and sample data created successfully.' AS Status FROM DUAL;
