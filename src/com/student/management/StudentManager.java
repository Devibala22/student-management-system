package com.student.management;

import java.util.*;
import java.io.*;
import java.awt.Desktop;

public class StudentManager {

    HashMap<Integer, Student> map = new HashMap<>();
    HashSet<String> courses = new HashSet<>();
    LinkedHashSet<Integer> order = new LinkedHashSet<>();
    Stack<Student> deletedStack = new Stack<>();

    private static final String FILE_NAME = "students.dat";

    public StudentManager() {
        loadFromFile();
    }

    // ADD
    public void addStudent(Student s) {
        map.put(s.id, s);
        courses.add(s.course);
        order.add(s.id);
        saveToFile();
    }

    // DISPLAY
    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID\tName\tCourse\n");
        sb.append("---------------------------\n");
        for (Student s : map.values()) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    // SEARCH BY ID
    public String search(int id) {
        return map.containsKey(id) ? "Found:\n" + map.get(id) : "Student Not Found";
    }

    // SEARCH BY NAME
    public String searchByName(String name) {
        StringBuilder sb = new StringBuilder();
        for (Student s : map.values()) {
            if (s.name.equalsIgnoreCase(name)) {
                sb.append(s).append("\n");
            }
        }
        return sb.length() == 0 ? "No student found" : sb.toString();
    }

    // DELETE
    public String delete(int id) {
        if (map.containsKey(id)) {
            Student s = map.remove(id);
            deletedStack.push(s);
            order.remove(id);
            saveToFile();
            return "Deleted Successfully";
        }
        return "Student Not Found";
    }

    // UNDO DELETE
    public String undoDelete() {
        if (!deletedStack.isEmpty()) {
            Student s = deletedStack.pop();
            map.put(s.id, s);
            courses.add(s.course);
            order.add(s.id);
            saveToFile();
            return "Undo Successful";
        }
        return "Nothing to Undo";
    }

    // COURSES
    public String showCourses() {
        return "Courses:\n" + courses;
    }

    // ORDER
    public String showOrder() {
        return "Insertion Order:\n" + order;
    }

    // SORT
    public String sortByName() {
        ArrayList<Student> list = new ArrayList<>(map.values());
        list.sort((a, b) -> a.name.compareToIgnoreCase(b.name));

        StringBuilder sb = new StringBuilder();
        sb.append("Sorted Students:\n");
        for (Student s : list) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    // COURSE STATS
    public String courseStats() {
        HashMap<String, Integer> count = new HashMap<>();
        for (Student s : map.values()) {
            count.put(s.course, count.getOrDefault(s.course, 0) + 1);
        }

        StringBuilder sb = new StringBuilder("Course-wise Count:\n");
        for (String c : count.keySet()) {
            sb.append(c).append(" : ").append(count.get(c)).append("\n");
        }
        return sb.toString();
    }

    // EXPORT TO FILE
    public void exportToFile() {
        try {
            FileWriter fw = new FileWriter("students.txt");
            fw.write(display());
            fw.close();
        } catch (Exception e) {
            System.out.println("Export Error");
        }
    }

    // OPEN FILE
    public String openFile() {
        try {
            File file = new File("students.txt");
            if (file.exists()) {
                Desktop.getDesktop().open(file);
                return "File Opened";
            }
            return "File not found";
        } catch (Exception e) {
            return "Error opening file";
        }
    }

    // SAVE
    public void saveToFile() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(map);
            oos.close();
        } catch (Exception e) {
            System.out.println("Save Error");
        }
    }

    // LOAD
    public void loadFromFile() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME));
            map = (HashMap<Integer, Student>) ois.readObject();
            ois.close();

            courses.clear();
            order.clear();

            for (Student s : map.values()) {
                courses.add(s.course);
                order.add(s.id);
            }

        } catch (Exception e) {
            System.out.println("No previous data");
        }
    }
}