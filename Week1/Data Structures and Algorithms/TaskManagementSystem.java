/* Exercise 5: Task Management System
 * Scenario: Manage tasks efficiently using a Singly Linked List.
 *
 * Linked List Types:
 * -> Singly Linked List  : Each node holds data + a pointer to the NEXT node.
 *                          Traversal is one-directional (head → tail).
 * -> Doubly Linked List  : Each node holds data + pointers to BOTH next and previous nodes.
 *                          Allows traversal in both directions, but uses more memory.
 *
 * Advantages of Linked Lists over Arrays:
 * -> Dynamic size  : Grows and shrinks at runtime without pre-allocation.
 * -> Efficient insertions/deletions at the head: O(1) — no element shifting needed.
 * -> No wasted memory from fixed-size allocation.
 *
 * Disadvantages:
 * -> No O(1) random access — must traverse from the head to reach a node: O(n).
 * -> Extra memory per node for storing the pointer to the next node. */

public class TaskManagementSystem {

    /* Task class representing a single task item */
    static class Task {
        private int taskId;
        private String taskName;
        private String status;  // e.g., "Pending", "In Progress", "Completed"
        Task next;              // Pointer to the next task in the linked list

        public Task(int taskId, String taskName, String status) {
            this.taskId = taskId;
            this.taskName = taskName;
            this.status = status;
            this.next = null;
        }

        public int getTaskId() { return taskId; }
        public String getTaskName() { return taskName; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        @Override
        public String toString() {
            return String.format("TaskID: %-5d | Name: %-25s | Status: %s", taskId, taskName, status);
        }
    }

    /* Singly Linked List implementation for Task management */
    static class TaskLinkedList {
        private Task head; // Head (first) node of the list

        public TaskLinkedList() {
            this.head = null;
        }

        /* Add a new task at the END of the list.
         * Time Complexity: O(n) — must traverse to find the tail. */
        public void addTask(Task task) {
            if (head == null) {
                head = task;
            } else {
                Task current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = task;
            }
            System.out.println("Added task: " + task.getTaskName() + " (ID: " + task.getTaskId() + ")");
        }

        /* Add a new task at the HEAD of the list (fast O(1) insertion).
         * Time Complexity: O(1) */
        public void addTaskAtHead(Task task) {
            task.next = head;
            head = task;
            System.out.println("Added task at head: " + task.getTaskName() + " (ID: " + task.getTaskId() + ")");
        }

        /* Search for a task by ID.
         * Time Complexity: O(n) — traverse from head until found or end reached. */
        public Task searchById(int taskId) {
            Task current = head;
            while (current != null) {
                if (current.getTaskId() == taskId) {
                    return current;
                }
                current = current.next;
            }
            return null; // Not found
        }

        /* Traverse and display all tasks in the list.
         * Time Complexity: O(n) — visits every node once. */
        public void traverseAll() {
            if (head == null) {
                System.out.println("Task list is empty.");
                return;
            }
            System.out.println("<----- Task List ----->");
            Task current = head;
            while (current != null) {
                System.out.println(current);
                current = current.next;
            }
            System.out.println("<-------------------->");
        }

        /* Delete a task by ID.
         * Time Complexity: O(n) — traverse to find the node, then update pointers.
         * Unlike arrays, no shifting is needed — only pointer adjustment. */
        public boolean deleteTask(int taskId) {
            if (head == null) {
                System.out.println("Task list is empty.");
                return false;
            }
            // Special case: delete the head node
            if (head.getTaskId() == taskId) {
                System.out.println("Deleted task: " + head.getTaskName() + " (ID: " + taskId + ")");
                head = head.next;
                return true;
            }
            // Find the node just before the one to delete
            Task current = head;
            while (current.next != null) {
                if (current.next.getTaskId() == taskId) {
                    System.out.println("Deleted task: " + current.next.getTaskName() + " (ID: " + taskId + ")");
                    current.next = current.next.next; // Bypass the deleted node
                    return true;
                }
                current = current.next;
            }
            System.out.println("Error: Task with ID " + taskId + " not found.");
            return false;
        }
    }

    public static void main(String[] args) {
        TaskLinkedList taskManager = new TaskLinkedList();

        System.out.println("=== Task Management System Demo ===\n");

        // Add tasks
        System.out.println("--- Adding Tasks ---");
        taskManager.addTask(new Task(1, "Design Database Schema", "Completed"));
        taskManager.addTask(new Task(2, "Develop REST API", "In Progress"));
        taskManager.addTask(new Task(3, "Write Unit Tests", "Pending"));
        taskManager.addTask(new Task(4, "Deploy to Staging", "Pending"));
        taskManager.addTaskAtHead(new Task(5, "Project Kickoff Meeting", "Completed"));

        // Traverse all tasks
        System.out.println("\n--- All Tasks ---");
        taskManager.traverseAll();

        // Search for a task
        System.out.println("\n--- Search by Task ID ---");
        int searchId = 2;
        Task found = taskManager.searchById(searchId);
        if (found != null) {
            System.out.println("Found: " + found);
        } else {
            System.out.println("Task with ID " + searchId + " not found.");
        }

        // Delete a task
        System.out.println("\n--- Delete Task ---");
        taskManager.deleteTask(3);
        System.out.println("\nTask list after deletion:");
        taskManager.traverseAll();

        // Try deleting a non-existent task
        System.out.println("\n--- Attempt to Delete Non-Existent Task (ID: 99) ---");
        taskManager.deleteTask(99);
    }
}

// -------- Analysis --------

/* Time Complexity of Singly Linked List Operations:
 * ┌────────────────────────┬────────────────────────────────────────────────────────────┐
 * │ Operation              │ Time Complexity                                            │
 * ├────────────────────────┼────────────────────────────────────────────────────────────┤
 * │ Add at Head            │ O(1) — Adjust head pointer only                           │
 * │ Add at Tail            │ O(n) — Traverse to the end                                │
 * │ Search                 │ O(n) — Linear scan from head                              │
 * │ Traverse               │ O(n) — Visit every node                                   │
 * │ Delete                 │ O(n) — Find + adjust pointers (no shifting)               │
 * └────────────────────────┴────────────────────────────────────────────────────────────┘
 *
 * Advantages of Linked Lists over Arrays for Dynamic Data:
 * -> Dynamic sizing: No need to pre-allocate memory; grows/shrinks as needed.
 * -> Efficient head insertion/deletion: O(1) vs O(n) for arrays (which need shifting).
 * -> Better for frequent additions and removals in large, unpredictable datasets.
 *
 * When Arrays are better:
 * -> When O(1) random access by index is needed.
 * -> When the dataset size is fixed and known upfront.
 * -> When memory efficiency is critical (arrays have no pointer overhead per element).
 */
