package rmit.assignment.api;

import rmit.assignment.entity.StudentEnrollment;

import java.util.List;

public interface StudentEnrolmentManager {
    public void add(StudentEnrollment studentEnrollment);

    public void delete(String studentName, String semester);


    public List<StudentEnrollment> getAll();

    public List<StudentEnrollment> getAllBy(String studentName);

    public List<StudentEnrollment> getAllBy(String studentName, String id);

}
