package rmit.assignment;

import rmit.assignment.entity.Course;
import rmit.assignment.entity.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    private List<Student> studentList = new ArrayList<>();
    private List<Course> courseList = new ArrayList<>();
    private String pattern = "yyyy-MM-dd";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


    public static void main(String[] args) {
        Main mainEntry = new Main();

        try {
            mainEntry.createStudentData();
        } catch (ParseException pe) {
            System.out.println("Can not parse date: " + pe);
        }

        mainEntry.createCourseDate();

    }

    private void createStudentData() throws ParseException {
        Date date = simpleDateFormat.parse("1997-09-09");
        Student hong = new Student(1, "Hong", date);

        date = simpleDateFormat.parse("1997-10-09");
        Student nam = new Student(2, "Nam", date);

        date = simpleDateFormat.parse("1997-11-08");
        Student tung = new Student(3, "Tung", date);

        date = simpleDateFormat.parse("1996-12-03");
        Student tuyet = new Student(4, "Tuyet", date);

        date = simpleDateFormat.parse("1994-9-1");
        Student hung = new Student(5, "Hung", date);

        studentList.add(hong);
        studentList.add(nam);
        studentList.add(tung);
        studentList.add(tuyet);
        studentList.add(hung);
    }

    private void createCourseDate() {
        Course math = new Course(1, "Math for computing", 12);
        Course programming = new Course(2, "Programming", 12);
        Course business = new Course(3, "Business", 12);
        Course webProgramming = new Course(4, "Web Programming", 12);
        Course robotic = new Course(5, "Robotic", 12);
        courseList.add(math);
        courseList.add(programming);
        courseList.add(business);
        courseList.add(webProgramming);
        courseList.add(robotic);

    }

}
