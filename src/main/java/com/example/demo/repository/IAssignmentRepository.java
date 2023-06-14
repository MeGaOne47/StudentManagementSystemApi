package com.example.demo.repository;

import com.example.demo.entity.Assignment;
import com.example.demo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAssignmentRepository extends JpaRepository<Assignment, Long>{
    List<Assignment> findByCourse(Course course);

    Optional<Assignment> findById(Long id);
}
