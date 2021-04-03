package rmit.assignment.impl;

import org.junit.Assert;
import org.junit.Test;
import rmit.assignment.entity.Course;
import rmit.assignment.entity.Student;
import rmit.assignment.entity.StudentEnrollment;

import java.util.Date;


public class StudentEnrolmentManagerImplTest {
    private StudentEnrolmentManagerImpl studentEnrolmentManager = new StudentEnrolmentManagerImpl();


    @Test
    public void testAdd() {
        //before calling add method
        Assert.assertEquals(0, studentEnrolmentManager.getAll().size());

        Date birthday = new Date(1997, 2, 5);

        Student student = new Student(1, "Nam", birthday);
        Course course = new Course(1, "Robotic", 12);
        String semester = "2015a";
        StudentEnrollment studentEnrollment = new StudentEnrollment(student, course, semester);
        studentEnrolmentManager.add(studentEnrollment);

        Assert.assertEquals(1, studentEnrolmentManager.getAll().size());

    }

    @Test
    public void testDelete() {
        //before calling method
        Assert.assertEquals(0, studentEnrolmentManager.getAll().size());
        Date birthday = new Date(1997, 2, 5);

        Student student = new Student(1, "Nam", birthday);
        Course course = new Course(1, "Robotic", 12);
        String semester = "2015a";
        StudentEnrollment studentEnrollment = new StudentEnrollment(student, course, semester);
        studentEnrolmentManager.add(studentEnrollment);
        Assert.assertEquals(1, studentEnrolmentManager.getAll().size());

        studentEnrolmentManager.delete("Nam", "2015a");
        Assert.assertEquals(0, studentEnrolmentManager.getAll().size());


    }

    @Test
    public void getAll() {
        //before calling add method
        Assert.assertEquals(0, studentEnrolmentManager.getAll().size());

        Date birthday = new Date(1997, 2, 5);
        Student student = new Student(1, "Nam", birthday);
        Course course = new Course(1, "Robotic", 12);
        String semester = "2015a";
        StudentEnrollment studentEnrollment = new StudentEnrollment(student, course, semester);
        studentEnrolmentManager.add(studentEnrollment);


        birthday = new Date(1997, 2, 5);
        Student student1 = new Student(1, "Nam", birthday);
        Course course1 = new Course(1, "Robotic", 12);
        String semester1 = "2015a";
        StudentEnrollment studentEnrollment1 = new StudentEnrollment(student1, course1, semester1);
        studentEnrolmentManager.add(studentEnrollment);

        Assert.assertEquals(2, studentEnrolmentManager.getAll().size());
    }

    @Test
    public void getAllBy() {
        Assert.assertEquals(0, studentEnrolmentManager.getAll().size());

        Date birthday = new Date(1997, 2, 5);
        Student student1 = new Student(1, "Nam", birthday);
        Course course1 = new Course(1, "Robotic", 12);
        String semester1 = "2015a";
        StudentEnrollment studentEnrollment1 = new StudentEnrollment(student1, course1, semester1);
        studentEnrolmentManager.add(studentEnrollment1);

        birthday = new Date(1997, 2, 5);
        Student student2 = new Student(1, "Tung", birthday);
        Course course2 = new Course(1, "Programming", 12);
        String semester2 = "2015a";
        StudentEnrollment studentEnrollment2 = new StudentEnrollment(student2, course2, semester2);
        studentEnrolmentManager.add(studentEnrollment2);

        Student student3 = new Student(1, "Nam", birthday);
        Course course3 = new Course(1, "Business", 12);
        String semester3 = "2015a";
        StudentEnrollment studentEnrollment3 = new StudentEnrollment(student3, course3, semester3);
        studentEnrolmentManager.add(studentEnrollment3);


        Assert.assertEquals(2, studentEnrolmentManager.getAllBy("Nam").size());
        Assert.assertEquals(1, studentEnrolmentManager.getAllBy("Tung").size());
        Assert.assertEquals(3, studentEnrolmentManager.getAll().size());
    }
}
