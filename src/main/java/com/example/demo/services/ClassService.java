package com.example.demo.services;

import com.example.demo.entity.ClassEntity;
import com.example.demo.entity.Student;
import com.example.demo.repository.IClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    @Autowired
    private IClassRepository classRepository;

    public List<ClassEntity> getAllClasses() {
        return classRepository.findAll();
    }

    public ClassEntity getClassById(Long id) {
        Optional<ClassEntity> optionalClass = classRepository.findById(id);
        return optionalClass.orElse(null);
    }

    public void addClass(ClassEntity classEntity) {
        classRepository.save(classEntity);
    }

    public void updateClass(ClassEntity classEntity) {
        classRepository.save(classEntity);
    }

    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }

    public ClassEntity getClassByName(String className) {
        return classRepository.findByClassName(className);
    }

    public List<ClassEntity> searchClasses(String keyword) {
        String searchKeyword = "%" + keyword.toLowerCase() + "%";
        return classRepository.searchByNameOrTeacherName(searchKeyword);
    }
}
