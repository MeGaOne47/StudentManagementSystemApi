package com.example.demo.entity;

public class AssignmentForm {
    private Long assignmentId;
    private Long studentId;

    // Default constructor
    public AssignmentForm() {
    }

    // Getters and setters
    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
