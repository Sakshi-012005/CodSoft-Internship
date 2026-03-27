import java.io.*;
import java.util.*;

// Student class
class Student implements Serializable {
    private String name;
    private String rollNumber;
    private String grade;

    public Student(String name, String rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    // Getters and setters
    public String getName() { return name; }
    public String getRollNumber() { return rollNumber; }
    public String getGrade() { return grade; }

    public void setName(String name) { this.name = name; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    public void setGrade(String grade) { this.grade = grade; }

    @Override
    public String toString() {
        return "Roll Number: " + rollNumber + ", Name: " + name + ", Grade: " + grade;
    }
}

//  Student Management System class
class SMS {
    private List<Student> students;
    private final String filename = "students.dat";

    public SMS() {
        students = new ArrayList<>();
        loadFromFile();
    }

    // Add student
    public void addStudent(Student s) {
        students.add(s);
        saveToFile();
        System.out.println("Student added successfully.");
    }

    // Remove student by roll number
    public void removeStudent(String rollNumber) {
        boolean removed = students.removeIf(s -> s.getRollNumber().equalsIgnoreCase(rollNumber));
        if (removed) {
            saveToFile();
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    // Search student by roll number
    public Student searchStudent(String rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber().equalsIgnoreCase(rollNumber)) {
                return s;
            }
        }
        return null;
    }

    // Display all students
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    // Save students to file
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }


    

    // Load students from file
    private void loadFromFile() {
        File file = new File(filename);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            students = (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}

//  Main class with console interface
public class StudentManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SMS sms = new SMS();
        int choice;

        do {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    if (name.isEmpty()) { System.out.println("Name cannot be empty."); break; }

                    System.out.print("Enter Roll Number: ");
                    String roll = sc.nextLine();
                    if (roll.isEmpty()) { System.out.println("Roll Number cannot be empty."); break; }

                    System.out.print("Enter Grade: ");
                    String grade = sc.nextLine();
                    if (grade.isEmpty()) { System.out.println("Grade cannot be empty."); break; }

                    sms.addStudent(new Student(name, roll, grade));
                    break;

                case 2:
                    System.out.print("Enter Roll Number to remove: ");
                    String rollRemove = sc.nextLine();
                    sms.removeStudent(rollRemove);
                    break;

                case 3:
                    System.out.print("Enter Roll Number to search: ");
                    String rollSearch = sc.nextLine();
                    Student s = sms.searchStudent(rollSearch);
                    if (s != null) System.out.println("Student Found: " + s);
                    else System.out.println("Student not found.");
                    break;

                case 4:
                    sms.displayAllStudents();
                    break;

                case 5:
                    System.out.println("Exiting Student Management System...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);

        sc.close();
    }
}