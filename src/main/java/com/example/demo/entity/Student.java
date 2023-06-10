package com.example.demo.entity;


import com.example.demo.Validator.anotation.ValidUserId;
import jakarta.persistence.*;


import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
//    @NotEmpty(message = "Name must not be empty")
//    @Size(max = 50, min = 1, message = "Name must be less than 50 characters")
    private String name;

    @Column(name = "age")
//    @NotNull(message = "Age is required")
//    @Min(value = 18, message = "Age must be greater than or equal to 18")
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setSource(Course course) {
        this.course = course;
    }

    @ManyToOne
    @JoinColumn(name = "Course_id")
//    @NotNull(message = "Course is required")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ValidUserId
    private User user;

    @Transient
    private String courseName;

    public String getCourseName() {
        if (course != null) {
            return course.getName();
        }
        return null;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
