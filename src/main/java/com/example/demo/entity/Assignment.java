package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column
    private Boolean submitted;

    @Column
    private Integer score;

    @Column
    private LocalDate deadline;

    @Column
    private String description;

    @Column
    private LocalDate dueDate;

    @Column
    private int grade;

    @Column
    private boolean assigned;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Lob
    @Column(name = "submitted_file", columnDefinition = "BLOB")
    private byte[] submittedFile;

    @OneToOne(mappedBy = "assignment", cascade = CascadeType.ALL)
    private AssignmentSubmission submission;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    private Student assignedTo;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // Constructors, getters, setters

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setAssignment(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setAssignment(null);
    }
}
