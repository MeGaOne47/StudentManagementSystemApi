package com.example.demo.repository;


import com.example.demo.entity.ClassEntity;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IClassRepository extends JpaRepository<ClassEntity, Long> {
    ClassEntity findByClassName(String className);

    @Query("SELECT t FROM ClassEntity t WHERE LOWER(t.className) LIKE :keyword OR LOWER(t.teacher.name) LIKE :keyword")
    List<ClassEntity> searchByNameOrTeacherName(@Param("keyword") String keyword);
}
