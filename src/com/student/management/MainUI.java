package com.student.management;

import java.awt.*;
import java.awt.event.*;

public class MainUI extends Frame implements ActionListener {

    TextField idField, nameField, courseField;
    TextArea output;

    Button addBtn, displayBtn, searchBtn, deleteBtn,
            coursesBtn, orderBtn, sortBtn, exportBtn,
            undoBtn, searchNameBtn, statsBtn, openFileBtn;

    StudentManager sm = new StudentManager();

    public MainUI() {

        setTitle("Student Management System");
        setSize(800, 550);
        setLayout(new BorderLayout(10, 10));


        // TOP INPUT PANEL

        Panel top = new Panel(new GridLayout(3, 2, 10, 10));

        top.add(new Label("ID:"));
        idField = new TextField();
        top.add(idField);

        top.add(new Label("Name:"));
        nameField = new TextField();
        top.add(nameField);

        top.add(new Label("Course:"));
        courseField = new TextField();
        top.add(courseField);

        add(top, BorderLayout.NORTH);

        // OUTPUT AREA

        output = new TextArea();

        output.setFont(new Font("Monospaced", Font.BOLD, 14));
        output.setBackground(Color.BLACK);
        output.setForeground(Color.GREEN);

        add(output, BorderLayout.CENTER);


        // BUTTON PANEL

        Panel bottom = new Panel(new GridLayout(3, 4, 10, 10));

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

        add(bottom, BorderLayout.SOUTH);


        // BUTTON EVENTS

        Button[] buttons = {
                addBtn, displayBtn, searchBtn, deleteBtn,
                coursesBtn, orderBtn, sortBtn, exportBtn,
                undoBtn, searchNameBtn, statsBtn, openFileBtn
        };

        for (Button b : buttons) {
            b.addActionListener(this);
        }


        // WINDOW CLOSE

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }


    // ACTION EVENTS

    public void actionPerformed(ActionEvent e) {

        try {

            // ================= ADD =================
            if (e.getSource() == addBtn) {

                if (idField.getText().trim().isEmpty() ||
                        nameField.getText().trim().isEmpty() ||
                        courseField.getText().trim().isEmpty()) {

                    output.setText("Please fill all fields!");
                    return;
                }

                int id = Integer.parseInt(idField.getText());

                Student s = new Student(
                        id,
                        nameField.getText(),
                        courseField.getText()
                );

                sm.addStudent(s);

                output.setText("Student Added Successfully!");

                clearFields();
            }

            // ================= DISPLAY =================
            else if (e.getSource() == displayBtn) {

                output.setText(sm.display());
            }

            // ================= SEARCH ID =================
            else if (e.getSource() == searchBtn) {

                if (idField.getText().trim().isEmpty()) {
                    output.setText("Enter ID!");
                    return;
                }

                int id = Integer.parseInt(idField.getText());

                output.setText(sm.search(id));
            }

            // ================= DELETE =================
            else if (e.getSource() == deleteBtn) {

                if (idField.getText().trim().isEmpty()) {
                    output.setText("Enter ID!");
                    return;
                }

                int id = Integer.parseInt(idField.getText());

                output.setText(sm.delete(id));
            }

            // ================= COURSES =================
            else if (e.getSource() == coursesBtn) {

                output.setText(sm.showCourses());
            }

            // ================= INSERTION ORDER =================
            else if (e.getSource() == orderBtn) {

                output.setText(sm.showOrder());
            }

            // ================= SORT =================
            else if (e.getSource() == sortBtn) {

                output.setText(sm.sortByName());
            }

            // ================= EXPORT =================
            else if (e.getSource() == exportBtn) {

                sm.exportToFile();

                output.setText("Exported to students.txt");
            }

            // ================= UNDO DELETE =================
            else if (e.getSource() == undoBtn) {

                output.setText(sm.undoDelete());
            }

            // ================= SEARCH NAME =================
            else if (e.getSource() == searchNameBtn) {

                String name = nameField.getText();

                if (name.trim().isEmpty()) {
                    output.setText("Enter Name!");
                    return;
                }

                output.setText(sm.searchByName(name));
            }

            // ================= STATS =================
            else if (e.getSource() == statsBtn) {

                output.setText(sm.courseStats());
            }

            // ================= OPEN FILE =================
            else if (e.getSource() == openFileBtn) {

                output.setText(sm.openFile());
            }

        }

        catch (NumberFormatException ex) {

            output.setText("ID must be a number!");

        }

        catch (Exception ex) {

            output.setText("Error: " + ex.getMessage());
        }
    }


    // CLEAR INPUT FIELDS

    public void clearFields() {

        idField.setText("");
        nameField.setText("");
        courseField.setText("");
    }


    // MAIN METHOD

    public static void main(String[] args) {

        new MainUI();
    }
}