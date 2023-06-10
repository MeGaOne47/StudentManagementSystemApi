package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "classes")
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name")
    private String className;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Column(name = "class_description")
    private String classDescription;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public ClassEntity() {
    }

    public ClassEntity(Long id, String className, String classDescription) {
        this.id = id;
        this.className = className;
        this.classDescription = classDescription;
    }

    public Long getId() {
        return id;
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
// Constructors, getters, and setters
}

