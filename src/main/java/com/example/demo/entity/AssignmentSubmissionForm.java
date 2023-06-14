package com.example.demo.entity;

public class AssignmentSubmissionForm {
    private Long studentId;
    private String submission;

    // Constructors, getters, and setters

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getSubmission() {
        return submission;
    }

    public void setSubmission(String submission) {
        this.submission = submission;
    }
}
