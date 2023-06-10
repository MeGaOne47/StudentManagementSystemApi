package com.example.demo.repository;

import com.example.demo.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITeacherRepository extends JpaRepository<Teacher, Long> {
}
