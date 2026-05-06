package com.student.management;

// Collection Framework imports
import java.util.*;

// File Handling imports
import java.io.*;

// Desktop API import for opening file automatically
import java.awt.Desktop;

public class StudentManager {

    // HashMap stores student data using ID as key
    // Fast searching -> O(1) average complexity
    HashMap<Integer, Student> map = new HashMap<>();

    // HashSet stores unique course names
    // Duplicate courses automatically removed
    HashSet<String> courses = new HashSet<>();

    // LinkedHashSet maintains insertion order
    LinkedHashSet<Integer> order = new LinkedHashSet<>();

    // Stack used for Undo Delete feature (LIFO)
    Stack<Student> deletedStack = new Stack<>();

    // File name used for serialization
    private static final String FILE_NAME = "students.dat";

    // Constructor
    // Automatically loads saved data when program starts
    public StudentManager() {
        loadFromFile();
    }


    // ADD STUDENT

    public void addStudent(Student s) {

        // Add student to HashMap
        // Key = student ID
        // Value = Student object
        map.put(s.id, s);

        // Add course to HashSet
        courses.add(s.course);

        // Store insertion order
        order.add(s.id);

        // Save data permanently
        saveToFile();
    }


    // DISPLAY ALL STUDENTS

    public String display() {

        // StringBuilder is efficient for string concatenation
        StringBuilder sb = new StringBuilder();

        sb.append("ID\tName\tCourse\n");
        sb.append("---------------------------\n");

        // map.values() returns all Student objects
        for (Student s : map.values()) {

            // append() adds data to StringBuilder
            sb.append(s).append("\n");
        }

        return sb.toString();
    }


    // SEARCH BY ID

    public String search(int id) {

        // containsKey() checks if ID exists
        return map.containsKey(id)

                // get() retrieves value using key
                ? "Found:\n" + map.get(id)

                : "Student Not Found";
    }

    // SEARCH BY NAME

    public String searchByName(String name) {

        StringBuilder sb = new StringBuilder();

        // Traverse all students
        for (Student s : map.values()) {

            // equalsIgnoreCase()
            // ignores uppercase/lowercase
            if (s.name.equalsIgnoreCase(name)) {

                sb.append(s).append("\n");
            }
        }

        // length()==0 means no results found
        if (sb.length() == 0) {
            return "No student found";
        }

        return sb.toString();
    }


    // DELETE STUDENT

    public String delete(int id) {

        // Check student exists
        if (map.containsKey(id)) {

            // remove() deletes and returns object
            Student s = map.remove(id);

            // Push deleted student into stack
            // Used for Undo operation
            deletedStack.push(s);

            // Remove from insertion order set
            order.remove(id);

            // Save updated data
            saveToFile();

            return "Deleted Successfully";
        }

        return "Student Not Found";
    }


    // UNDO DELETE

    public String undoDelete() {

        // isEmpty() checks stack empty or not
        if (!deletedStack.isEmpty()) {

            // pop() removes top element from stack
            Student s = deletedStack.pop();

            // Restore student
            map.put(s.id, s);

            courses.add(s.course);

            order.add(s.id);

            saveToFile();

            return "Undo Successful";
        }

        return "Nothing to Undo";
    }

    // SHOW UNIQUE COURSES

    public String showCourses() {

        // HashSet automatically removes duplicates
        return "Courses:\n" + courses;
    }


    // SHOW INSERTION ORDER

    public String showOrder() {

        // LinkedHashSet maintains insertion order
        return "Insertion Order:\n" + order;
    }


    // SORT BY NAME

    public String sortByName() {

        // Convert map values into ArrayList
        ArrayList<Student> list = new ArrayList<>(map.values());

        // Lambda expression for custom sorting
        // compareToIgnoreCase() ignores case sensitivity
        list.sort((a, b) ->
                a.name.compareToIgnoreCase(b.name));

        StringBuilder sb = new StringBuilder();

        sb.append("Sorted Students:\n");

        for (Student s : list) {

            sb.append(s).append("\n");
        }

        return sb.toString();
    }


    // COURSE-WISE STATISTICS

    public String courseStats() {

        // Stores course count
        HashMap<String, Integer> count = new HashMap<>();

        for (Student s : map.values()) {

            // getOrDefault()
            // returns existing value OR default value
            count.put(
                    s.course,
                    count.getOrDefault(s.course, 0) + 1
            );
        }

        StringBuilder sb =
                new StringBuilder("Course-wise Count:\n");

        // keySet() returns all keys
        for (String c : count.keySet()) {

            sb.append(c)
                    .append(" : ")
                    .append(count.get(c))
                    .append("\n");
        }

        return sb.toString();
    }


    // EXPORT TO TEXT FILE

    public void exportToFile() {

        try {

            // FileWriter writes text into file
            FileWriter fw =
                    new FileWriter("students.txt");

            // Write display data into file
            fw.write(display());

            // close() prevents memory leak
            fw.close();

        } catch (Exception e) {

            System.out.println("Export Error");
        }
    }


    // OPEN EXPORTED FILE

    public String openFile() {

        try {

            // File object reference
            File file = new File("students.txt");

            // exists() checks file availability
            if (file.exists()) {

                // Opens file using system default app
                Desktop.getDesktop().open(file);

                return "File Opened";
            }

            return "File not found";

        } catch (Exception e) {

            return "Error opening file";
        }
    }


    // SAVE USING SERIALIZATION

    public void saveToFile() {

        try {

            // ObjectOutputStream writes objects into file
            ObjectOutputStream oos =
                    new ObjectOutputStream(
                            new FileOutputStream(FILE_NAME)
                    );

            // writeObject() serializes HashMap
            oos.writeObject(map);

            oos.close();

        } catch (Exception e) {

            System.out.println("Save Error");
        }
    }


    // LOAD USING DESERIALIZATION

    public void loadFromFile() {

        try {

            // ObjectInputStream reads objects
            ObjectInputStream ois =
                    new ObjectInputStream(
                            new FileInputStream(FILE_NAME)
                    );

            // readObject() converts byte stream into object
            map = (HashMap<Integer, Student>)
                    ois.readObject();

            ois.close();

            // Clear old data
            courses.clear();
            order.clear();

            // Rebuild HashSet and LinkedHashSet
            for (Student s : map.values()) {

                courses.add(s.course);

                order.add(s.id);
            }

        } catch (Exception e) {

            // Executes first time when file absent
            System.out.println("No previous data");
        }
    }
}