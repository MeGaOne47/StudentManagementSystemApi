package com.example.demo.repository;

import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Long> {
    Course findByName(String name);

    @Query("SELECT t FROM Course t WHERE LOWER(t.name) LIKE :keyword ")
    List<Course> searchByName(@Param("keyword") String keyword);
}
