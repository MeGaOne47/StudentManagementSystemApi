package com.example.demo.repository;


import com.example.demo.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClassRepository extends JpaRepository<ClassEntity, Long> {
    ClassEntity findByClassName(String className);
}
