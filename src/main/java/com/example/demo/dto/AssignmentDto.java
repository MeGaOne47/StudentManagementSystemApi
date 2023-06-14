package com.example.demo.dto;

import java.time.LocalDate;

public class AssignmentDto {
    private Long id;
    private String title;
    private String description;

    // Các trường khác của bài tập, ví dụ: dueDate, grade

    public AssignmentDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public AssignmentDto(Long id, String title, String description, LocalDate dueDate, int grade) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.grade = grade;
    }

    private LocalDate dueDate;
    private int grade;

    // constructors, getters, setters
}
