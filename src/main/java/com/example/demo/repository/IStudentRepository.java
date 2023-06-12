package com.example.demo.repository;

import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT t FROM Student t WHERE LOWER(t.name) LIKE :keyword OR LOWER(t.course.name) LIKE :keyword")
    List<Student> searchByNameOrCourseName(@Param("keyword") String keyword);

}
