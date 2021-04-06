package rmit.assignment.impl;

import rmit.assignment.api.StudentEnrolmentManager;
import rmit.assignment.entity.StudentEnrollment;

import java.util.ArrayList;
import java.util.List;

public class StudentEnrolmentManagerImpl implements StudentEnrolmentManager {


    private List<StudentEnrollment> studentEnrollmentList = new ArrayList<>();


    @Override
    public void add(StudentEnrollment studentEnrollment) {
        studentEnrollmentList.add(studentEnrollment);
    }

    @Override
    public void delete(String studentName, String semester) {
        for (int i = 0; i < studentEnrollmentList.size(); i++) {
            StudentEnrollment item = studentEnrollmentList.get(i);
            if (item.getStudent().getName().equalsIgnoreCase(studentName) && item.getSemester().equalsIgnoreCase(semester)) {
                studentEnrollmentList.remove(item);

            }
        }

    }

    @Override
    public List<StudentEnrollment> getAll() {
        return studentEnrollmentList;
    }

    @Override
    public List<StudentEnrollment> getAllBy(String studentName) {
        List<StudentEnrollment> result = new ArrayList<>();
        for (StudentEnrollment item : studentEnrollmentList) {
            if (item.getStudent().getName().equalsIgnoreCase(studentName)) {
                result.add(item);
            }
        }

        return result;
    }

    @Override
    public List<StudentEnrollment> getAllBy(String studentName, int id) {
        List<StudentEnrollment> result = new ArrayList<>();
        for (StudentEnrollment item : studentEnrollmentList) {
            if (item.getStudent().getName().equalsIgnoreCase(studentName) && item.getStudent().getId() == id) {
                result.add(item);
            }
        }

        return result;
    }

}
