package com.example.demo.dto;

public class CourseDto {
    private Long id;
    private String name;
    private String description;

    private String email;
    // Constructors, getters, and setters

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Default constructor
    public CourseDto() {
    }

    public CourseDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Constructor with fields
    public CourseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and setters
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
}

