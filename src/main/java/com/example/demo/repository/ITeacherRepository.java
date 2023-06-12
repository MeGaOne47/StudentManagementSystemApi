package com.example.demo.repository;

import com.example.demo.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByName(String name);

    @Query("SELECT t FROM Teacher t WHERE LOWER(t.name) LIKE :keyword OR LOWER(t.course.name) LIKE :keyword")
    List<Teacher> searchByNameOrCourseName(@Param("keyword") String keyword);

}
