package com.example.demo.dto;

public class ClassDto {
    private Long id;
    private String name;
    private String description;
    private String teacherName;

    public ClassDto() {
    }

    public ClassDto(Long id, String name, String description, String teacherName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.teacherName = teacherName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherName() {
        return teacherName;
    }
}
