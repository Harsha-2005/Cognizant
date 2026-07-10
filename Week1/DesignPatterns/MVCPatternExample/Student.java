package MVCPatternExample;

/**
 * Student - Model Class
 *
 * Represents the data (model) in the MVC pattern.
 * Contains only data and no business logic or UI code.
 */
public class Student {

    private String name;
    private String id;
    private String grade;

    public Student(String name, String id, String grade) {
        this.name  = name;
        this.id    = id;
        this.grade = grade;
    }

    public String getName()  { return name; }
    public String getId()    { return id; }
    public String getGrade() { return grade; }

    public void setName(String name)   { this.name = name; }
    public void setId(String id)       { this.id = id; }
    public void setGrade(String grade) { this.grade = grade; }
}
