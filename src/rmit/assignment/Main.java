package rmit.assignment;

import rmit.assignment.api.StudentEnrolmentManager;
import rmit.assignment.entity.Course;
import rmit.assignment.entity.Student;
import rmit.assignment.entity.StudentEnrollment;
import rmit.assignment.impl.StudentEnrolmentManagerImpl;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    private List<Student> studentList = new ArrayList<>();
    private List<Course> courseList = new ArrayList<>();
    private String pattern = "yyyy-MM-dd";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    private StudentEnrolmentManager studentEnrolmentManager = new StudentEnrolmentManagerImpl();
    private static final String UPDATE = "UPDATE";
    private static final String ENROLL = "ENROLL";
    private static final String DELETE = "DELETE";
    private static final String ADD = "ADD";
    private static String[] HEADERS = {"STUDENT", "COURSE", "SEMESTER"};

    public static void main(String[] args) {
        Main mainEntry = new Main();
        mainEntry.createData();
        mainEntry.processAction();

    }

    private void print() {
        try {

            FileWriter csvWriter = new FileWriter("C:\\assignment\\report.csv");

            //Print by student name
            printByStudentName(csvWriter);
            csvWriter.append("\n");

            //Print by course name
            printByCourseName(csvWriter);
            csvWriter.append("\n");

            //Print by course semester
            printBySemester(csvWriter);
            csvWriter.append("\n");

            csvWriter.flush();
            csvWriter.close();
        } catch (FileNotFoundException fe) {
            System.out.println("Can not read this file");
        } catch (IOException e) {
            System.out.println("Can not parse this file");
        }
    }

    //This method is utilized for printing information of course and student by semester
    private void printBySemester(FileWriter csvWriter) throws IOException {
        csvWriter.append("\n");
        csvWriter.append("SEMESTER");
        csvWriter.append(",");
        csvWriter.append("STUDENT NAME");
        csvWriter.append(",");
        csvWriter.append("COURSE NAME");
        csvWriter.append("\n");
        Map<String, List<StudentEnrollment>> map = new HashMap<>();
        List<StudentEnrollment> studentEnrollmentList = studentEnrolmentManager.getAll();
        for (StudentEnrollment item : studentEnrollmentList) {
            List<StudentEnrollment> list = map.get(item.getSemester());
            if (list == null) {
                list = new ArrayList<>();
                map.put(item.getSemester(), list);
            }
            list.add(item);
        }
        for (Map.Entry<String, List<StudentEnrollment>> entry : map.entrySet()) {
            String semester = entry.getKey();
            List<StudentEnrollment> list = entry.getValue();

            for (int i = 0; i < list.size(); i++) {
                StudentEnrollment item = list.get(i);
                if (i == 0) {
                    csvWriter.append(semester + "," + item.getStudent().getName() + "," + item.getCourse().getName());
                } else {
                    csvWriter.append("" + "," + item.getStudent().getName() + "," + item.getCourse().getName());
                }
                csvWriter.append("\n");
            }
            csvWriter.append("\n");

        }
    }

    //This method is utilized for printing information of course and semester by student name
    private void printByStudentName(FileWriter csvWriter) throws IOException {
        csvWriter.append("STUDENT NAME");
        csvWriter.append(",");
        csvWriter.append("COURSE NAME");
        csvWriter.append(",");
        csvWriter.append("SEMESTER");
        csvWriter.append("\n");
        Map<String, List<StudentEnrollment>> map = new HashMap<>();
        List<StudentEnrollment> studentEnrollmentList = studentEnrolmentManager.getAll();
        for (StudentEnrollment item : studentEnrolmentManager.getAll()) {
            List<StudentEnrollment> list = map.get(item.getStudent().getName());
            if (list == null) {
                list = new ArrayList<>();
                map.put(item.getStudent().getName(), list);
            }
            list.add(item);
        }
        for (Map.Entry<String, List<StudentEnrollment>> entry : map.entrySet()) {
            String name = entry.getKey();
            List<StudentEnrollment> list = entry.getValue();

            for (int i = 0; i < list.size(); i++) {
                StudentEnrollment item = list.get(i);
                if (i == 0) {
                    csvWriter.append(name + "," + item.getCourse().getName() + "," + item.getSemester());
                } else {
                    csvWriter.append("" + "," + item.getCourse().getName() + "," + item.getSemester());
                }
                csvWriter.append("\n");
            }
            csvWriter.append("\n");

        }
    }

    //This method is utilized for printing information of semester and student by course name
    private void printByCourseName(FileWriter csvWriter) throws IOException {
        csvWriter.append("\n");
        csvWriter.append("COURSE NAME");
        csvWriter.append(",");
        csvWriter.append("STUDENT NAME");
        csvWriter.append(",");
        csvWriter.append("SEMESTER");
        csvWriter.append("\n");
        Map<String, List<StudentEnrollment>> map = new HashMap<>();
        List<StudentEnrollment> studentEnrollmentList = studentEnrolmentManager.getAll();
        for (StudentEnrollment item : studentEnrollmentList) {
            List<StudentEnrollment> list = map.get(item.getCourse().getName());
            if (list == null) {
                list = new ArrayList<>();
                map.put(item.getCourse().getName(), list);
            }
            list.add(item);
        }
        for (Map.Entry<String, List<StudentEnrollment>> entry : map.entrySet()) {
            String courseName = entry.getKey();
            List<StudentEnrollment> list = entry.getValue();

            for (int i = 0; i < list.size(); i++) {
                StudentEnrollment item = list.get(i);
                if (i == 0) {
                    csvWriter.append(courseName + "," + item.getStudent().getName() + "," + item.getSemester());
                } else {
                    csvWriter.append("" + "," + item.getStudent().getName() + "," + item.getSemester());
                }
                csvWriter.append("\n");
            }
            csvWriter.append("\n");

        }
    }

    // This method is utilized for checking the validation of the input of student name
    private void checkStudentNameIsValid(String studentName) throws Exception {
        for (Student student : studentList) {
            if (student.getName().trim().equalsIgnoreCase(studentName)) {
                return;
            }
        }
        throw new Exception();
    }

    // This method is utilized for checking the validation of the input of course name
    private void checkCourseNameIsValid(String courseName) throws Exception {
        for (Course course : courseList) {
            if (course.getName().trim().equalsIgnoreCase(courseName)) {
                return;
            }
        }
        throw new Exception();
    }

    // This method is used for asking users "UPDATE" or "ENROLL" and checking validation of their input
    private void processAction() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Do you want to 'UPDATE' or 'ENROLL'? ");
        String action = myObj.nextLine();  // Read user input

        if (action.trim().equalsIgnoreCase(UPDATE)) {
            update();
        } else if (action.trim().equalsIgnoreCase(ENROLL)) {
            processEnroll();
        } else {
            System.out.println("This input is invalid in this system !!!");
            processAction();
        }

    }

    // This function is utilized to ask users when they want to continue or not
    private void checkContinuing() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Do you want to continue 'Y' or 'N'? ");
        String confirmation = myObj.nextLine();  // Read user input

        if (confirmation.trim().equalsIgnoreCase("Y")) {
            processAction();
        } else if (confirmation.trim().equalsIgnoreCase("N")) {
            print();
            System.out.println(" All your course are already printed into csv file !!!");
        } else {
            System.out.println("Your choice is invalid !!!");
            System.out.println("Try Again With (Y/N) only !");
            checkContinuing();
        }

    }

    private void processEnroll() {
        //enter student, id and course
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter Student Name (Ex: 'Hong', 'Nam', 'Tung', 'Tuyet', 'Hung'): ");
        String studentName = myObj.nextLine();  // Read user input
        try {
            checkStudentNameIsValid(studentName);
        } catch (Exception e) {
            System.out.println("This student name is not valid in this system !! ");
            processEnroll();
            return;
        }

        enroll(studentName);
    }

    // This method is used for updating from specific student name
    private void update() {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter Student Name (Ex: 'Hong', 'Nam', 'Tung', 'Tuyet', 'Hung'): ");
        String studentName = scanner.nextLine();  // Read user input
        try {
            checkStudentNameIsValid(studentName);
            System.out.println("------------------------");
            System.out.println("The list course & semester of" + " " + studentName + " " + "is:");
            System.out.println("------------------------");
            viewEnrollmentBy(studentName);
            checkAddAndDeleteInputInvalid(studentName);

        } catch (Exception e) {
            System.out.println("This student name is not valid in this system !! ");
            update();
            return;
        }

    }

    // This method is utilized for checking the validation of the "ADD" or "DELETE" input from users
    private void checkAddAndDeleteInputInvalid(String studentName) {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Do you want to 'ADD' or 'DELETE' course ? ");
        String confirmation = scanner.nextLine();  // Read user input
        if (confirmation.trim().equalsIgnoreCase(DELETE)) {
            scanner = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Enter semester that you want to delete: ");
            String semester = scanner.nextLine();  // Read user input
            studentEnrolmentManager.delete(studentName, semester);
            System.out.println("The list of course after deleting is: ");
            System.out.println("------------------------");
            viewEnrollmentBy(studentName);
            checkContinuing();
        } else if (confirmation.trim().equalsIgnoreCase(ADD)) {
            enroll(studentName);
        } else {
            System.out.println("Your input is invalid in the system !!! ");
            checkAddAndDeleteInputInvalid(studentName);
        }

    }

    // This function is utilized for viewing the list of course and semester of student after enrolling or adding
    private void viewEnrollmentBy(String studentName) {
        List<StudentEnrollment> studentEnrollmentList = studentEnrolmentManager.getAllBy(studentName);
        for (StudentEnrollment item : studentEnrollmentList) {
            System.out.println("Semester: " + item.getSemester());
            System.out.println("Course name: " + item.getCourse().getName());
            System.out.println("------------------------");
        }

    }

    // This function is used for enrolling student
    private void enroll(String studentName) {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter Course (Ex: 'Math for computing', 'Robotic', 'Database concept', 'Business', 'Programming'): ");
        String courseName = myObj.nextLine();
        try {
            checkCourseNameIsValid(courseName);
        } catch (Exception e) {
            System.out.println("This course name is not valid in this system !! ");
            enroll(studentName);
            return;
        }

        System.out.println("Enter Semester: ");
        String semester = myObj.nextLine();
        Student student = findStudentBy(studentName);
        Course course = findCourseBy(courseName);
        StudentEnrollment studentEnrollment = new StudentEnrollment(student, course, semester);
        studentEnrolmentManager.add(studentEnrollment);
        System.out.println("------------------------");
        System.out.println("The list course of" + " " + studentName + " " + "after adding/enrolling is: ");
        System.out.println("------------------------");
        viewEnrollmentBy(studentName);
        checkContinuing();
    }

    private void createData() {
        try {
            createStudentData();
        } catch (ParseException pe) {
            System.out.println("Can not parse date: " + pe);
        }

        createCourseData();

    }

    // This method is utilized for creating data of student including id, name and birthday
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

    // This method is utilized for creating data of course including id, name and number of credit
    private void createCourseData() {
        Course math = new Course(1, "Math for computing", 12);
        Course programming = new Course(2, "Programming", 12);
        Course business = new Course(3, "Business", 12);
        Course webProgramming = new Course(4, "Database concept", 12);
        Course robotic = new Course(5, "Robotic", 12);
        courseList.add(math);
        courseList.add(programming);
        courseList.add(business);
        courseList.add(webProgramming);
        courseList.add(robotic);

    }

    private Student findStudentBy(String name) {
        for (Student item : studentList) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }

        return null;
    }

    private Course findCourseBy(String name) {
        for (Course item : courseList) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

}
