package MVCPatternExample;

/**
 * Main - Tests the MVC Pattern for student record management.
 *
 * Demonstrates the separation of concerns:
 * -> Model      (Student)           : Data only
 * -> View       (StudentView)       : Display only
 * -> Controller (StudentController) : Logic + coordination
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== MVC Pattern - Student Record Management Demo ===\n");

        // Create Model
        Student student = new Student("Alice Johnson", "S-1001", "A");

        // Create View
        StudentView view = new StudentView();

        // Create Controller — wires Model and View together
        StudentController controller = new StudentController(student, view);

        // Display initial student details
        System.out.println("--- Initial Student Record ---");
        controller.updateView();

        // Update student details via the Controller
        System.out.println("\n--- Updating Student Details ---");
        controller.setStudentName("Alice M. Johnson");
        controller.setStudentGrade("A+");

        // Display updated record
        System.out.println("\n--- Updated Student Record ---");
        controller.updateView();

        // Another update — change student ID and grade
        System.out.println("\n--- Further Updates ---");
        controller.setStudentId("S-2024-1001");
        controller.setStudentGrade("A+ (Honours)");

        System.out.println("\n--- Final Student Record ---");
        controller.updateView();

        // Read values through the controller
        System.out.println("\n--- Reading values via Controller getters ---");
        System.out.println("Name  : " + controller.getStudentName());
        System.out.println("ID    : " + controller.getStudentId());
        System.out.println("Grade : " + controller.getStudentGrade());
    }
}
