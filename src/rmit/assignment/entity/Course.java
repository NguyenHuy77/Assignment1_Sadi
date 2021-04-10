package rmit.assignment.entity;

//This class define the information of courses

public class Course {
    private String id;
    private String name;
    private int numberOfCredit;

    public Course(String id, String name, int numberOfCredit) {
        this.id = id;
        this.name = name;
        this.numberOfCredit = numberOfCredit;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNumberOfCredit(int numberOfCredit) {
        this.numberOfCredit = numberOfCredit;
    }

    public int getNumberOfCredit() {
        return numberOfCredit;
    }

}

