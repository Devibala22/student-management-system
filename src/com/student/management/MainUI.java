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
        setSize(700, 500);
        setLayout(new BorderLayout(10,10));

        // INPUT PANEL
        Panel top = new Panel(new GridLayout(3,2,10,10));
        top.add(new Label("ID:")); idField = new TextField(); top.add(idField);
        top.add(new Label("Name:")); nameField = new TextField(); top.add(nameField);
        top.add(new Label("Course:")); courseField = new TextField(); top.add(courseField);

        add(top, BorderLayout.NORTH);

        // OUTPUT
        output = new TextArea();
        output.setFont(new Font("Monospaced", Font.BOLD, 14));
        output.setBackground(Color.BLACK);
        output.setForeground(Color.GREEN);
        add(output, BorderLayout.CENTER);

        // BUTTON PANEL
        Panel bottom = new Panel(new GridLayout(3,4,10,10));

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

        bottom.add(addBtn); bottom.add(displayBtn); bottom.add(searchBtn); bottom.add(deleteBtn);
        bottom.add(coursesBtn); bottom.add(orderBtn); bottom.add(sortBtn); bottom.add(exportBtn);
        bottom.add(undoBtn); bottom.add(searchNameBtn); bottom.add(statsBtn); bottom.add(openFileBtn);

        add(bottom, BorderLayout.SOUTH);

        // EVENTS
        for (Button b : new Button[]{addBtn,displayBtn,searchBtn,deleteBtn,coursesBtn,orderBtn,sortBtn,exportBtn,undoBtn,searchNameBtn,statsBtn,openFileBtn}) {
            b.addActionListener(this);
        }

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        try {
            int id = Integer.parseInt(idField.getText());

            if (e.getSource() == addBtn)
                sm.addStudent(new Student(id, nameField.getText(), courseField.getText()));

            else if (e.getSource() == displayBtn)
                output.setText(sm.display());

            else if (e.getSource() == searchBtn)
                output.setText(sm.search(id));

            else if (e.getSource() == deleteBtn)
                output.setText(sm.delete(id));

            else if (e.getSource() == coursesBtn)
                output.setText(sm.showCourses());

            else if (e.getSource() == orderBtn)
                output.setText(sm.showOrder());

            else if (e.getSource() == sortBtn)
                output.setText(sm.sortByName());

            else if (e.getSource() == exportBtn) {
                sm.exportToFile();
                output.setText("Exported to students.txt");
            }

            else if (e.getSource() == undoBtn)
                output.setText(sm.undoDelete());

            else if (e.getSource() == searchNameBtn)
                output.setText(sm.searchByName(nameField.getText()));

            else if (e.getSource() == statsBtn)
                output.setText(sm.courseStats());

            else if (e.getSource() == openFileBtn)
                output.setText(sm.openFile());

        } catch (Exception ex) {
            output.setText("Invalid Input!");
        }
    }

    public static void main(String[] args) {
        new MainUI();
    }
}