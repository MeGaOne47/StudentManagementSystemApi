package com.example.demo.dto;

public class ClassDto {
    private Long id;
    private String className;
    private String classDescription;

    private TeacherDto teacher;

    public TeacherDto getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDto teacher) {
        this.teacher = teacher;
    }
// Constructors, getters, and setters

    public Long getId() {
        return id;
    }

    public ClassDto() {
    }

    public ClassDto(Long id, String className, String classDescription) {
        this.id = id;
        this.className = className;
        this.classDescription = classDescription;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }
}
