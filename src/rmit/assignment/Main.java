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
    private List<String> semesterList = new ArrayList<>();
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
        csvWriter.append("STUDENT ID");
        csvWriter.append(",");
        csvWriter.append("STUDENT NAME");
        csvWriter.append(",");
        csvWriter.append("ID COURSE");
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
                    csvWriter.append(semester + "," + item.getStudent().getId() + "," + item.getStudent().getName() + "," + item.getCourse().getId() + "," + item.getCourse().getName());
                } else {
                    csvWriter.append("" + "," + item.getStudent().getId() + "," + item.getStudent().getName() + "," + item.getCourse().getId() + "," + item.getCourse().getName());
                }
                csvWriter.append("\n");
            }
            csvWriter.append("\n");

        }
    }

    //This method is utilized for printing information of course and semester by student name
    private void printByStudentName(FileWriter csvWriter) throws IOException {
        csvWriter.append("STUDENT ID");
        csvWriter.append(",");
        csvWriter.append("STUDENT NAME");
        csvWriter.append(",");
        csvWriter.append("ID COURSE");
        csvWriter.append(",");
        csvWriter.append("COURSE NAME");
        csvWriter.append(",");
        csvWriter.append("SEMESTER");
        csvWriter.append("\n");
        Map<String, List<StudentEnrollment>> map = new HashMap<>();
        List<StudentEnrollment> studentEnrollmentList = studentEnrolmentManager.getAll();
        for (StudentEnrollment item : studentEnrolmentManager.getAll()) {
            List<StudentEnrollment> list = map.get(item.getStudent().getId());
            if (list == null) {
                list = new ArrayList<>();
                map.put(item.getStudent().getId(), list);
            }
            list.add(item);
        }
        for (Map.Entry<String, List<StudentEnrollment>> entry : map.entrySet()) {
            String id = entry.getKey();
            List<StudentEnrollment> list = entry.getValue();

            for (int i = 0; i < list.size(); i++) {
                StudentEnrollment item = list.get(i);
                if (i == 0) {
                    csvWriter.append(item.getStudent().getId() + "," + item.getStudent().getName() + "," + item.getCourse().getId() + "," + item.getCourse().getName() + "," + item.getSemester());
                } else {
                    csvWriter.append("" + "," + item.getStudent().getName() + "," + item.getCourse().getId() + "," + item.getCourse().getName() + "," + item.getSemester());
                }
                csvWriter.append("\n");
            }
            csvWriter.append("\n");

        }
    }

    //This method is utilized for printing information of semester and student by course name
    private void printByCourseName(FileWriter csvWriter) throws IOException {
        csvWriter.append("\n");
        csvWriter.append("ID COURSE");
        csvWriter.append(",");
        csvWriter.append("COURSE NAME");
        csvWriter.append(",");
        csvWriter.append("STUDENT ID");
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
                    csvWriter.append(courseName + "," + item.getCourse().getId() + "," + item.getStudent().getId() + "," + item.getStudent().getName() + "," + item.getSemester());
                } else {
                    csvWriter.append("" + "," + item.getStudent().getId() + "," + item.getStudent().getName() + "," + item.getSemester());
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
        System.out.println("Enter Student Name (Ex: 'Hong', 'Nam', 'Tung', 'Tuyet', 'Hung', 'Huy'): ");
        String studentName = myObj.nextLine();  // Read user input
        try {
            checkStudentNameIsValid(studentName);
            checkIdInvalid(myObj, studentName);

        } catch (Exception e) {
            System.out.println("This student name is not valid in this system !! ");
            processEnroll();
            return;
        }

        String id = getIdByStudentName(myObj, studentName);

        enroll(studentName, id);
    }

    // This method is used for updating from specific student name
    private void update() {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter Student Name (Ex: 'Hong', 'Nam', 'Tung', 'Tuyet', 'Hung', 'Hung', 'Huy'): ");
        String studentName = scanner.nextLine();  // Read user input
        try {
            checkStudentNameIsValid(studentName);
            checkIdInvalid(scanner, studentName);
            String id = getIdByStudentName(scanner, studentName);
            System.out.println("------------------------");
            System.out.println("The information of" + " " + studentName + " " + ":");
            System.out.println("------------------------");
            viewEnrollmentBy(studentName, id);
            checkAddAndDeleteInputInvalid(studentName, id);

        } catch (Exception e) {
            System.out.println("This student name is not valid in this system !! ");
            update();
            return;
        }


    }

    // This method is utilized for checking the validation of the "ADD" or "DELETE" input from users
    private void checkAddAndDeleteInputInvalid(String studentName, String id) {
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Do you want to 'ADD' or 'DELETE' course ? ");
        String confirmation = scanner.nextLine();  // Read user input
        if (confirmation.trim().equalsIgnoreCase(DELETE)) {
            scanner = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Enter semester that you want to delete (Ex: '2020A','2020B', '2020C', '2021A', '2021B', '2021C): ");
            String semester = scanner.nextLine();  // Read user input
            studentEnrolmentManager.delete(studentName, semester);
            System.out.println("The information of" + " " + studentName + " " + "after deleting is: ");
            System.out.println("------------------------");
            viewEnrollmentBy(studentName, id);
            checkContinuing();
        } else if (confirmation.trim().equalsIgnoreCase(ADD)) {
            enroll(studentName, id);
        } else {
            System.out.println("Your input is invalid in the system !!! ");
            checkAddAndDeleteInputInvalid(studentName, id);
        }

    }

    // This function is utilized for viewing the list of course and semester of student after enrolling or adding
    private void viewEnrollmentBy(String studentName, String id) {
        List<StudentEnrollment> studentEnrollmentList = studentEnrolmentManager.getAllBy(studentName, id);
        for (StudentEnrollment item : studentEnrollmentList) {
            System.out.println("Student name:" + studentName);
            System.out.println("Student ID:" + item.getStudent().getId());
            System.out.println("Course name: " + item.getCourse().getName());
            System.out.println("ID course: " + item.getCourse().getId());
            System.out.println("Semester: " + item.getSemester());
            System.out.println("Credit number: 12");
            System.out.println("------------------------");
        }

    }

    // This function is used for enrolling student
    private void enroll(String studentName, String id) {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter Course (Ex: 'Math for computing', 'Programming', 'Business', 'Database concept', 'Robotic', 'Vietnamese'): ");
        String courseName = myObj.nextLine();
        try {
            checkCourseNameIsValid(courseName);
        } catch (Exception e) {
            System.out.println("This course name is not valid in this system !! ");
            enroll(studentName, id);
            return;
        }
        checkCourseIdValid(myObj, courseName);
        String semesterName = checkSemesterValid();

        Student student = findStudentBy(studentName, id);
        Course course = findCourseBy(courseName);


        StudentEnrollment studentEnrollment = new StudentEnrollment(student, course, semesterName);
        studentEnrolmentManager.add(studentEnrollment);
        System.out.println("------------------------");
        System.out.println("The information of" + " " + studentName + " " + "after adding/enrolling: ");
        System.out.println("------------------------");
        viewEnrollmentBy(studentName, id);
        checkContinuing();
    }

    private void createData() {
        try {
            createStudentData();
        } catch (ParseException pe) {
            System.out.println("Can not parse date: " + pe);
        }

        createCourseData();
        createSemester();

    }

    // This method is utilized for creating data of student including id, name and birthday
    private void createStudentData() throws ParseException {
        Date date = simpleDateFormat.parse("1997-09-09");
        Student hong = new Student("S3655207", "Hong", date);

        date = simpleDateFormat.parse("1997-10-09");
        Student nam = new Student("S3655698", "Nam", date);

        date = simpleDateFormat.parse("1997-11-08");
        Student tung = new Student("S3526321", "Tung", date);

        date = simpleDateFormat.parse("1996-12-03");
        Student tuyet = new Student("S5896321", "Tuyet", date);

        date = simpleDateFormat.parse("1994-9-1");
        Student hung = new Student("S3265896", "Hung", date);

        date = simpleDateFormat.parse("1995-8-1");
        Student hung1 = new Student("S3265154", "Huy", date);

        studentList.add(hong);
        studentList.add(nam);
        studentList.add(tung);
        studentList.add(tuyet);
        studentList.add(hung);
        studentList.add(hung1);
    }

    // This method is utilized for creating data of course including id, name and number of credit
    private void createCourseData() {
        Course math = new Course("COSC4030", "Math for computing", 12);
        Course programming = new Course("PHYS2153", "Programming", 12);
        Course business = new Course("BUS2232", "Business", 12);
        Course webProgramming = new Course("COSC3215", "Database concept", 12);
        Course robotic = new Course("COSC2012", "Robotic", 12);
        Course databaseConcept = new Course("PHC2564", "Vietnamese", 12);
        courseList.add(math);
        courseList.add(programming);
        courseList.add(business);
        courseList.add(webProgramming);
        courseList.add(robotic);
        courseList.add(databaseConcept);


    }

    private void createSemester() {
        semesterList.add("2020A");
        semesterList.add("2020B");
        semesterList.add("2020C");
        semesterList.add("2021A");
        semesterList.add("2021B");
        semesterList.add("2021C");

    }

    private Student findStudentBy(String name, String id) {
        for (Student item : studentList) {
            if (item.getName().equalsIgnoreCase(name) && item.getId().equalsIgnoreCase(id)) {
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


    // This method is utilized for checking whether the student name is duplicate or not
    private String getIdByStudentName(Scanner myObj, String studentName) {
        int numberOfStudent = 0;
        for (Student student : studentList) {
            if (student.getName().trim().equalsIgnoreCase(studentName)) {
                numberOfStudent++;
            }
        }
        String id = "";
        if (numberOfStudent > 1) {
            id = checkIdInvalid(myObj, studentName);
        } else {
            for (Student student : studentList) {
                if (student.getName().equalsIgnoreCase(studentName)) {
                    id = student.getId();
                    break;
                }
            }
        }

        return id;
    }

    private String checkIdInvalid(Scanner myObj, String studentName) {
        System.out.println("Enter ID of student (Ex:'S3655207', 'S3655698', 'S3526321', 'S5896321', 'S3265896', 'S3265154'): ");
        String id = myObj.nextLine();  // Read user input
        for (Student item : studentList) {
            if (item.getId().trim().equalsIgnoreCase(id) && item.getName().equalsIgnoreCase(studentName)) {
                return id;
            }
        }
        System.out.println("Your input is invalid !!!!");
        return checkIdInvalid(myObj, studentName);
    }

    private String checkSemesterValid() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter Semester (Ex: '2020A','2020B', '2020C', '2021A', '2021B', '2021C): ");
        String semesterName = myObj.nextLine();  // Read user input
        for (String item : semesterList) {
            if (item.trim().equalsIgnoreCase(semesterName)) {
                return semesterName;
            } else {
                System.out.println("Your input is invalid !!!!!");
                return checkSemesterValid();
            }
        }
        return null;

    }

    private String getIdByCourseName(Scanner myObj, String idCourse) {
        int numberOfcourse = 0;
        for (Course course : courseList) {
            if (course.getId().trim().equalsIgnoreCase(idCourse)) {
                numberOfcourse++;
            }
        }
        String id = "";
        if (numberOfcourse > 1) {
            id = checkIdInvalid(myObj, idCourse);
        } else {
            for (Course course : courseList) {
                if (course.getId().equalsIgnoreCase(idCourse)) {
                    id = course.getId();
                    break;
                }
            }
        }

        return id;

    }

    private String checkCourseIdValid(Scanner myObj1, String courName) {
        System.out.println("Enter ID of course (Ex: 'COSC4030', 'PHYS2153', 'BUS2232', 'COSC3215', 'COSC2012', 'PHC2564'): ");
        String idcourse = myObj1.nextLine();  // Read user input
        for (Course course : courseList) {
            if (course.getId().trim().equalsIgnoreCase(idcourse) && course.getName().equalsIgnoreCase(courName)) {
                return idcourse;
            }
        }
        System.out.println("Your input is invalid !!!!!");
        return checkCourseIdValid(myObj1, courName);
    }
}