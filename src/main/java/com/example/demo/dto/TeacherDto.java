package com.example.demo.dto;

import java.util.List;

public class TeacherDto {
    private Long id;
    private String name;
    private String age;
    private String courseName;



    // Constructors, getters, and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // Constructors
    public TeacherDto() {
    }

    public TeacherDto(Long id, String name, String age, String courseName) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.courseName = courseName;
    }

    // Getters and Setters
    // ...

    // toString method
    // ...

}

