package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class AssignmentSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String attachedFile;
    private String notes;

    private String submission;

    public Long getId() {
        return id;
    }

    public AssignmentSubmission() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(String attachedFile) {
        this.attachedFile = attachedFile;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }
// Các trường khác của bài nộp

    @OneToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    // Constructors, getters và setters
}

