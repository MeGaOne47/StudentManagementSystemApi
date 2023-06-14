package com.example.demo.dto;

public class AssignmentSubmissionDto {
    private Long id;
    private String attachedFile;

    private String notes;
    // Các trường khác của bài nộp

    public Long getId() {
        return id;
    }

    public AssignmentSubmissionDto() {
    }

    public AssignmentSubmissionDto(Long id, String attachedFile, String notes) {
        this.id = id;
        this.attachedFile = attachedFile;
        this.notes = notes;
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

    // Constructors, getters, setters
}

