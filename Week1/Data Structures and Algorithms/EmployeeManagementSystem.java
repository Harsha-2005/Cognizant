/* Array Representation in Memory:
 * -> Arrays store elements in contiguous memory locations.
 * -> Each element is accessed via an index in O(1) time (random access) because the
 *    memory address is computed as: base_address + (index × element_size).
 * -> Advantages of Arrays:
 *    1. O(1) random access by index.
 *    2. Cache-friendly due to contiguous memory layout.
 *    3. Simple and low memory overhead (no pointers needed).
 * -> Limitations:
 *    1. Fixed size — must be declared upfront.
 *    2. Insertion/deletion in the middle requires O(n) shifting of elements.
 *    3. Searching an unsorted array is O(n).
 * -> Best used when: data size is known, random access is frequent, and insertions/deletions
 *    at arbitrary positions are rare. */

public class EmployeeManagementSystem {

    /* Employee class with required attributes */
    static class Employee {
        private int employeeId;
        private String name;
        private String position;
        private double salary;

        public Employee(int employeeId, String name, String position, double salary) {
            this.employeeId = employeeId;
            this.name = name;
            this.position = position;
            this.salary = salary;
        }

        public int getEmployeeId() {
            return employeeId;
        }

        public String getName() {
            return name;
        }

        public String getPosition() {
            return position;
        }

        public double getSalary() {
            return salary;
        }

        @Override
        public String toString() {
            return String.format("ID: %-5d | Name: %-20s | Position: %-18s | Salary: $%.2f",
                    employeeId, name, position, salary);
        }
    }

    /* EmployeeArray manages a fixed-size array of Employee records */
    static class EmployeeArray {
        private Employee[] employees;
        private int count; // Current number of stored employees
        private int capacity; // Maximum capacity of the array

        public EmployeeArray(int capacity) {
            this.capacity = capacity;
            this.employees = new Employee[capacity];
            this.count = 0;
        }

        /*
         * Add a new employee to the next available slot.
         * Time Complexity: O(1) amortized — just place at next index.
         * If array is full, reports an error.
         */
        public boolean addEmployee(Employee emp) {
            if (count >= capacity) {
                System.out.println("Error: Employee array is full. Cannot add " + emp.getName());
                return false;
            }
            employees[count] = emp;
            count++;
            System.out.println("Added: " + emp.getName() + " (ID: " + emp.getEmployeeId() + ")");
            return true;
        }

        /*
         * Search for an employee by ID using linear search.
         * Time Complexity: O(n) — must scan the array until a match is found.
         */
        public Employee searchById(int id) {
            for (int i = 0; i < count; i++) {
                if (employees[i].getEmployeeId() == id) {
                    return employees[i];
                }
            }
            return null; // Not found
        }

        /*
         * Traverse and display all employees.
         * Time Complexity: O(n) — visits every element once.
         */
        public void traverseAll() {
            if (count == 0) {
                System.out.println("No employees found.");
                return;
            }
            System.out.println("<----- Employee Records ----->");
            for (int i = 0; i < count; i++) {
                System.out.println(employees[i]);
            }
            System.out.println("<--------------------------->");
        }

        /*
         * Delete an employee by ID.
         * Time Complexity: O(n) — search is O(n), then shifting remaining elements is
         * O(n).
         */
        public boolean deleteEmployee(int id) {
            int index = -1;
            // Find the index of the employee to delete
            for (int i = 0; i < count; i++) {
                if (employees[i].getEmployeeId() == id) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                System.out.println("Error: Employee with ID " + id + " not found.");
                return false;
            }
            // Shift all elements after the deleted position one step to the left
            for (int i = index; i < count - 1; i++) {
                employees[i] = employees[i + 1];
            }
            employees[count - 1] = null; // Clear the last slot
            count--;
            System.out.println("Deleted employee with ID: " + id);
            return true;
        }
    }

    public static void main(String[] args) {
        EmployeeArray empSystem = new EmployeeArray(10);

        System.out.println("=== Employee Management System Demo ===\n");

        // Add employees
        System.out.println("--- Adding Employees ---");
        empSystem.addEmployee(new Employee(1, "Alice Johnson", "Software Engineer", 95000.00));
        empSystem.addEmployee(new Employee(2, "Bob Smith", "Project Manager", 110000.00));
        empSystem.addEmployee(new Employee(3, "Carol White", "QA Analyst", 75000.00));
        empSystem.addEmployee(new Employee(4, "David Brown", "DevOps Engineer", 88000.00));
        empSystem.addEmployee(new Employee(5, "Eve Davis", "UI/UX Designer", 80000.00));

        // Traverse all employees
        System.out.println("\n--- All Employees ---");
        empSystem.traverseAll();

        // Search for an employee
        System.out.println("\n--- Search by Employee ID ---");
        int searchId = 3;
        Employee found = empSystem.searchById(searchId);
        if (found != null) {
            System.out.println("Found: " + found);
        } else {
            System.out.println("Employee with ID " + searchId + " not found.");
        }

        // Delete an employee
        System.out.println("\n--- Delete Employee ---");
        empSystem.deleteEmployee(2);
        System.out.println("\nEmployee list after deletion:");
        empSystem.traverseAll();

        // Search for the deleted employee
        System.out.println("\n--- Search for deleted employee (ID: 2) ---");
        Employee deletedSearch = empSystem.searchById(2);
        if (deletedSearch != null) {
            System.out.println("Found: " + deletedSearch);
        } else {
            System.out.println("Employee with ID 2 not found (successfully deleted).");
        }
    }
}

// -------- Analysis --------

/*
 * Time Complexity of Array Operations:
 * ┌────────────┬────────────────────────────────────────────────────────┐
 * │ Operation │ Time Complexity │
 * ├────────────┼────────────────────────────────────────────────────────┤
 * │ Add │ O(1) — Insert at the end (no shifting needed) │
 * │ Search │ O(n) — Linear scan through the array │
 * │ Traverse │ O(n) — Visit every element │
 * │ Delete │ O(n) — Find element + shift remaining elements left │
 * └────────────┴────────────────────────────────────────────────────────┘
 *
 * Limitations of Arrays & When to Use Them:
 * -> Limitation 1: Fixed size — Arrays are statically allocated. For dynamic
 * sizing, use ArrayList or LinkedList.
 * -> Limitation 2: Slow insertion/deletion in middle — O(n) due to element
 * shifting.
 * -> Limitation 3: No built-in support for complex operations like sorting or
 * searching by multiple fields.
 *
 * When to use arrays:
 * -> When the number of elements is known and fixed in advance.
 * -> When fast random access by index (O(1)) is the primary operation.
 * -> When memory efficiency is critical (arrays have minimal overhead compared
 * to linked structures).
 */
