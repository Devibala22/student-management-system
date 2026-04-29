package com.student.management;

import java.io.Serializable;

public class Student implements Serializable {
    int id;
    String name;
    String course;

    public Student(int id, String name, String course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }

    public String toString() {
        return id + "\t" + name + "\t" + course;
    }
}