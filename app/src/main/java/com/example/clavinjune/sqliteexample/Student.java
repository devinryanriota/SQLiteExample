package com.example.clavinjune.sqliteexample;

public class Student {
    private String nim, name;
    private Double gpa;

    public Student(String nim, String name, Double gpa) {
        this.nim = nim;
        this.name = name;
        this.gpa = gpa;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }
}
