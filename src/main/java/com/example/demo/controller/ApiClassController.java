package com.example.demo.controller;

import com.example.demo.dto.ClassDto;
import com.example.demo.entity.ClassEntity;
import com.example.demo.entity.Teacher;
import com.example.demo.services.ClassService;
import com.example.demo.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/classes")
public class ApiClassController {
    @Autowired
    private ClassService classService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<ClassDto>> getAllClasses() {
        List<ClassEntity> classes = classService.getAllClasses();
        List<ClassDto> classDTOs = new ArrayList<>();
        for (ClassEntity cls : classes) {
            classDTOs.add(convertToClassDto(cls));
        }
        return ResponseEntity.ok(classDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassDto> getClassById(@PathVariable Long id) {
        ClassEntity cls = classService.getClassById(id);
        if (cls != null) {
            ClassDto classDto = convertToClassDto(cls);
            return ResponseEntity.ok(classDto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClass(@PathVariable Long id) {
        if (classService.getClassById(id) != null) {
            classService.deleteClass(id);
            return ResponseEntity.ok("Class deleted successfully!");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> addClass(@RequestBody ClassDto classDto) {
        try {
            // Convert ClassDto to ClassEntity
            ClassEntity cls = convertToClassEntity(classDto);
            classService.addClass(cls);
            return ResponseEntity.ok("Class added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add class.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateClass(@PathVariable Long id, @RequestBody ClassDto classDto) {
        ClassEntity existingClass = classService.getClassById(id);
        if (existingClass != null) {
            ClassEntity updatedClass = convertToClassEntity(classDto);
            updatedClass.setId(existingClass.getId());
            classService.updateClass(updatedClass);
            return ResponseEntity.ok("Class updated successfully!");
        }
        return ResponseEntity.notFound().build();
    }

    private ClassDto convertToClassDto(ClassEntity cls) {
        ClassDto classDto = new ClassDto();
        classDto.setId(cls.getId());
        classDto.setName(cls.getClassName());
        classDto.setDescription(cls.getClassDescription());

        Teacher teacher = cls.getTeacher();
        if (teacher != null) {
            classDto.setTeacherName(teacher.getName());
        }

        return classDto;
    }


    private ClassEntity convertToClassEntity(ClassDto classDto) {
        ClassEntity cls = new ClassEntity();
        cls.setClassName(classDto.getName());
        cls.setClassDescription(classDto.getDescription());

        // Lấy giáo viên từ tên giáo viên trong classDto
        String teacherName = classDto.getTeacherName();
        if (teacherName != null && !teacherName.isEmpty()) {
            Teacher teacher = teacherService.getTeacherByName(teacherName);
            if (teacher != null) {
                cls.setTeacher(teacher);
            }
        }

        return cls;
    }

}
