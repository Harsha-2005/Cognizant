package MVCPatternExample;

/**
 * StudentController - Controller Class
 *
 * Acts as the bridge between the Model (Student) and the View (StudentView).
 * Handles all user requests, updates the model, and triggers the view to refresh.
 *
 * Key Responsibilities:
 * -> Reads data from the Model
 * -> Updates data in the Model
 * -> Instructs the View to display updated data
 */
public class StudentController {

    private final Student student;     // Model reference
    private final StudentView view;    // View reference

    public StudentController(Student student, StudentView view) {
        this.student = student;
        this.view    = view;
    }

    // ---- Getters (read from Model) ----

    public String getStudentName()  { return student.getName(); }
    public String getStudentId()    { return student.getId(); }
    public String getStudentGrade() { return student.getGrade(); }

    // ---- Setters (update Model) ----

    public void setStudentName(String name) {
        student.setName(name);
        System.out.println("[Controller] Student name updated to: " + name);
    }

    public void setStudentId(String id) {
        student.setId(id);
        System.out.println("[Controller] Student ID updated to: " + id);
    }

    public void setStudentGrade(String grade) {
        student.setGrade(grade);
        System.out.println("[Controller] Student grade updated to: " + grade);
    }

    /**
     * Instructs the View to display the current state of the Model.
     */
    public void updateView() {
        view.displayStudentDetails(
                student.getName(),
                student.getId(),
                student.getGrade()
        );
    }
}
