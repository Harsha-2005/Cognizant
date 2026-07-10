package MVCPatternExample;

/**
 * StudentView - View Class
 *
 * Responsible ONLY for displaying student data on screen.
 * It has no knowledge of how data is stored or updated — it just renders what it receives.
 */
public class StudentView {

    /**
     * Displays the full details of a student in a formatted layout.
     * @param name  The student's name
     * @param id    The student's ID
     * @param grade The student's grade
     */
    public void displayStudentDetails(String name, String id, String grade) {
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│       Student Record             │");
        System.out.println("├─────────────────────────────────┤");
        System.out.printf( "│  Name  : %-23s│%n", name);
        System.out.printf( "│  ID    : %-23s│%n", id);
        System.out.printf( "│  Grade : %-23s│%n", grade);
        System.out.println("└─────────────────────────────────┘");
    }
}
