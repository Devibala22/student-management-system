package com.student.management;

import java.awt.*;
import java.awt.event.*;

public class MainUI extends Frame implements ActionListener {

    // Input fields
    TextField idField, nameField, courseField;

    // Output area
    TextArea output;

    // Buttons
    Button addBtn, displayBtn, searchBtn, deleteBtn,
            coursesBtn, orderBtn, sortBtn, exportBtn,
            undoBtn, searchNameBtn, statsBtn, openFileBtn;

    // Student manager object
    StudentManager sm = new StudentManager();

    public MainUI() {

        // Frame title
        setTitle("Student Management System");

        // Frame size
        setSize(800, 550);

        // Main layout
        setLayout(new BorderLayout(10, 10));



        // Top input panel

        Panel top = new Panel(new GridLayout(3, 2, 10, 10));

        // ID field
        top.add(new Label("ID:"));
        idField = new TextField();
        top.add(idField);

        // Name field
        top.add(new Label("Name:"));
        nameField = new TextField();
        top.add(nameField);

        // Course field
        top.add(new Label("Course:"));
        courseField = new TextField();
        top.add(courseField);

        // Add top panel to frame
        add(top, BorderLayout.NORTH);



        // Output area

        output = new TextArea();

        // Output text style
        output.setFont(new Font("Monospaced", Font.BOLD, 14));

        // Background color
        output.setBackground(Color.BLACK);

        // Text color
        output.setForeground(Color.GREEN);

        // Add output area
        add(output, BorderLayout.CENTER);



        // Bottom button panel

        Panel bottom = new Panel(new GridLayout(3, 4, 10, 10));

        // Create buttons
        addBtn = new Button("Add");
        displayBtn = new Button("Display");
        searchBtn = new Button("Search ID");
        deleteBtn = new Button("Delete");

        coursesBtn = new Button("Courses");
        orderBtn = new Button("Order");
        sortBtn = new Button("Sort");
        exportBtn = new Button("Export");

        undoBtn = new Button("Undo");
        searchNameBtn = new Button("Search Name");
        statsBtn = new Button("Stats");
        openFileBtn = new Button("Open File");

        // Add buttons to panel
        bottom.add(addBtn);
        bottom.add(displayBtn);
        bottom.add(searchBtn);
        bottom.add(deleteBtn);

        bottom.add(coursesBtn);
        bottom.add(orderBtn);
        bottom.add(sortBtn);
        bottom.add(exportBtn);

        bottom.add(undoBtn);
        bottom.add(searchNameBtn);
        bottom.add(statsBtn);
        bottom.add(openFileBtn);

        // Add bottom panel
        add(bottom, BorderLayout.SOUTH);



        // Add action listeners to buttons

        Button[] buttons = {
                addBtn, displayBtn, searchBtn, deleteBtn,
                coursesBtn, orderBtn, sortBtn, exportBtn,
                undoBtn, searchNameBtn, statsBtn, openFileBtn
        };

        for (Button b : buttons) {

            // Register button click event
            b.addActionListener(this);
        }



        // Close window event

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                // Close frame
                dispose();
            }
        });

        // Make frame visible
        setVisible(true);
    }



    // Button click actions

    public void actionPerformed(ActionEvent e) {

        try {

            // Add student
            if (e.getSource() == addBtn) {

                // Check empty fields
                if (idField.getText().trim().isEmpty() ||
                        nameField.getText().trim().isEmpty() ||
                        courseField.getText().trim().isEmpty()) {

                    output.setText("Please fill all fields!");
                    return;
                }

                // Convert ID to integer
                int id = Integer.parseInt(idField.getText());

                // Create student object
                Student s = new Student(
                        id,
                        nameField.getText(),
                        courseField.getText()
                );

                // Add student
                sm.addStudent(s);

                output.setText("Student Added Successfully!");

                // Clear fields
                clearFields();
            }

            // Display all students
            else if (e.getSource() == displayBtn) {

                output.setText(sm.display());
            }

            // Search using ID
            else if (e.getSource() == searchBtn) {

                // Check ID empty
                if (idField.getText().trim().isEmpty()) {

                    output.setText("Enter ID!");
                    return;
                }

                int id = Integer.parseInt(idField.getText());

                // Search student
                output.setText(sm.search(id));
            }

            // Delete student
            else if (e.getSource() == deleteBtn) {

                // Check ID empty
                if (idField.getText().trim().isEmpty()) {

                    output.setText("Enter ID!");
                    return;
                }

                int id = Integer.parseInt(idField.getText());

                // Delete student
                output.setText(sm.delete(id));
            }

            // Show unique courses
            else if (e.getSource() == coursesBtn) {

                output.setText(sm.showCourses());
            }

            // Show insertion order
            else if (e.getSource() == orderBtn) {

                output.setText(sm.showOrder());
            }

            // Sort students by name
            else if (e.getSource() == sortBtn) {

                output.setText(sm.sortByName());
            }

            // Export data to text file
            else if (e.getSource() == exportBtn) {

                sm.exportToFile();

                output.setText("Exported to students.txt");
            }

            // Undo delete
            else if (e.getSource() == undoBtn) {

                output.setText(sm.undoDelete());
            }

            // Search student by name
            else if (e.getSource() == searchNameBtn) {

                String name = nameField.getText();

                // Check name empty
                if (name.trim().isEmpty()) {

                    output.setText("Enter Name!");
                    return;
                }

                output.setText(sm.searchByName(name));
            }

            // Show course statistics
            else if (e.getSource() == statsBtn) {

                output.setText(sm.courseStats());
            }

            // Open exported file
            else if (e.getSource() == openFileBtn) {

                output.setText(sm.openFile());
            }

        }

        // Invalid number input
        catch (NumberFormatException ex) {

            output.setText("ID must be a number!");
        }

        // Other errors
        catch (Exception ex) {

            output.setText("Error: " + ex.getMessage());
        }
    }



    // Clear text fields

    public void clearFields() {

        idField.setText("");
        nameField.setText("");
        courseField.setText("");
    }



    // Program starting point

    public static void main(String[] args) {

        new MainUI();
    }
}