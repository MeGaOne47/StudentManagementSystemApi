package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public Comment(Long id, String content, LocalDateTime createdAt, Assignment assignment) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.assignment = assignment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    public Comment() {
        this.createdAt = LocalDateTime.now();
    }

    public Comment(String content) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    // Constructors, getters, setters
}
