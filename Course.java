package cstask4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return "Course: " + courseCode + " - " + title + "\nDescription: " + description + "\nCapacity: " +
                capacity + " Schedule: " + schedule;
    }

	public Student[] getregisteredCourses() {
		// TODO Auto-generated method stub
		return null;
	}
}

class Student {
    private int studentId;
    private String name;
    private List<Course> registeredCourses;

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerForCourse(Course course) {
        registeredCourses.add(course);
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
    }

    public void displayRegisteredCourses() {
        System.out.println("Registered Courses for " + name + " (ID: " + studentId + "):");
        if (registeredCourses.isEmpty()) {
            System.out.println("No registered courses.");
        } else {
            for (Course course : registeredCourses) {
                System.out.println(course.getCourseCode() + " - " + course.getTitle());
            }
        }
    }
}

class CourseManager {
    List<Course> courses;

    public CourseManager() {
        this.courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void displayCourseListing() {
        System.out.println("Available Courses:");
        for (Course course : courses) {
            int availableSlots = course.getCapacity() - countRegisteredStudents(course);
            System.out.println(course.toString() + "\nAvailable Slots: " + availableSlots + "\n");
        }
    }

    int countRegisteredStudents(Course course) {
        int count = 0;
        for (Student student : course.getregisteredCourses()) {
            count++;
        }
        return count;
    }
}

 class CourseRegistrationSystem {
    public static void main(String[] args) {
        CourseManager courseManager = new CourseManager();

        Course c1 = new Course("CS101", "Introduction to Programming", "Basic programming concepts", 30, "Mon/Wed 10:00 AM");
        Course c2 = new Course("MAT202", "Linear Algebra", "Fundamentals of linear algebra", 25, "Tue/Thu 2:00 PM");
        Course c3 = new Course("ENG105", "English Composition", "Writing skills and composition", 20, "Mon/Fri 1:00 PM");

        courseManager.addCourse(c1);
        courseManager.addCourse(c2);
        courseManager.addCourse(c3);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Display Course Listing");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    courseManager.displayCourseListing();
                    break;
                case 2:
                    registerForCourse(scanner, courseManager);
                    break;
                case 3:
                    dropCourse(scanner, courseManager);
                    break;
                case 4:
                    System.out.println("Exiting. Thank you!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void registerForCourse(Scanner scanner, CourseManager courseManager) {
        System.out.print("Enter your student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        Student student = new Student(studentId, name);

        System.out.println("Available Courses:");
        courseManager.displayCourseListing();

        System.out.print("Enter the course code you want to register for: ");
        String courseCode = scanner.next();

        Course selectedCourse = findCourseByCode(courseManager, courseCode);

        if (selectedCourse != null) {
            int availableSlots = selectedCourse.getCapacity() - courseManager.countRegisteredStudents(selectedCourse);
            if (availableSlots > 0) {
                student.registerForCourse(selectedCourse);
                System.out.println("Registration successful for " + selectedCourse.getTitle());
            } else {
                System.out.println("Registration failed. No available slots for " + selectedCourse.getTitle());
            }
        } else {
            System.out.println("Invalid course code. Please enter a valid course code.");
        }
    }

    private static void dropCourse(Scanner scanner, CourseManager courseManager) {
        System.out.print("Enter your student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter the course code you want to drop: ");
        String courseCode = scanner.next();

        Course selectedCourse = findCourseByCode(courseManager, courseCode);

        if (selectedCourse != null) {
            Student student = findStudentById(selectedCourse, studentId);
            if (student != null) {
                student.dropCourse(selectedCourse);
                System.out.println("Course dropped successfully.");
            } else {
                System.out.println("Student not registered for this course.");
            }
        } else {
            System.out.println("Invalid course code. Please enter a valid course code.");
        }
    }

    private static Course findCourseByCode(CourseManager courseManager, String courseCode) {
        for (Course course : courseManager.courses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }

    private static Student findStudentById(Course course, int studentId) {
        for (Student student : course.getregisteredCourses()) {
            if (student.getStudentId() == studentId) {
                return student;
            }
        }
        return null;
    }
}
