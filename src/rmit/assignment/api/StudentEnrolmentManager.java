package rmit.assignment.api;

import rmit.assignment.entity.StudentEnrollment;

import java.util.List;

public interface StudentEnrolmentManager {
    public void add(StudentEnrollment studentEnrollment);

    public void update(StudentEnrollment studentEnrollment);

    public void delete(StudentEnrollment studentEnrollment);

    public StudentEnrollment getOne();

    public List<StudentEnrollment> getAll();

}
