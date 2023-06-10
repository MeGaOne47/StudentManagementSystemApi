package com.example.demo.controller;

import com.example.demo.dto.ClassDto;
import com.example.demo.dto.TeacherDto;
import com.example.demo.entity.ClassEntity;
import com.example.demo.entity.Teacher;
import com.example.demo.services.ClassService;
import com.example.demo.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        List<ClassDto> classDtos = classes.stream()
                .map(this::convertToClassDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(classDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassDto> getClassById(@PathVariable Long id) {
        ClassEntity classEntity = classService.getClassById(id);
        if (classEntity == null) {
            return ResponseEntity.notFound().build();
        }
        ClassDto classDto = convertToClassDto(classEntity);
        return ResponseEntity.ok(classDto);
    }

    @PostMapping
    public ResponseEntity<ClassDto> addClass(@RequestBody ClassDto classDto) {
        ClassEntity classEntity = convertToClassEntity(classDto);

        // Lấy giáo viên từ ID
        Teacher teacher = teacherService.getTeacherById(classDto.getTeacher().getId());
        classEntity.setTeacher(teacher);

        classService.addClass(classEntity);
        classDto.setId(classEntity.getId());

        // Lấy thông tin giáo viên và gán cho classDto
        TeacherDto teacherDto = convertToTeacherDto(teacher);
        classDto.setTeacher(teacherDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(classDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassDto> updateClass(@PathVariable Long id, @RequestBody ClassDto classDto) {
        ClassEntity existingClassEntity = classService.getClassById(id);
        if (existingClassEntity == null) {
            return ResponseEntity.notFound().build();
        }
        ClassEntity updatedClassEntity = convertToClassEntity(classDto);

        // Lấy giáo viên từ ID
        Teacher teacher = teacherService.getTeacherById(classDto.getTeacher().getId());
        updatedClassEntity.setTeacher(teacher);

        updatedClassEntity.setId(existingClassEntity.getId());
        classService.updateClass(updatedClassEntity);

        // Lấy thông tin giáo viên và gán cho classDto
        TeacherDto teacherDto = convertToTeacherDto(teacher);
        classDto.setTeacher(teacherDto);

        return ResponseEntity.ok(classDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        ClassEntity classEntity = classService.getClassById(id);
        if (classEntity == null) {
            return ResponseEntity.notFound().build();
        }
        classService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }

    private ClassDto convertToClassDto(ClassEntity classEntity) {
        ClassDto classDto = new ClassDto();
        classDto.setId(classEntity.getId());
        classDto.setClassName(classEntity.getClassName());
        classDto.setClassDescription(classEntity.getClassDescription());

        // Lấy thông tin giáo viên và gán cho classDto
        Teacher teacher = classEntity.getTeacher();
        if (teacher != null) {
            TeacherDto teacherDto = convertToTeacherDto(teacher);
            classDto.setTeacher(teacherDto);
        }

        return classDto;
    }

    private ClassEntity convertToClassEntity(ClassDto classDto) {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setClassName(classDto.getClassName());
        classEntity.setClassDescription(classDto.getClassDescription());
        return classEntity;
    }

    private TeacherDto convertToTeacherDto(Teacher teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacher.getId());
        teacherDto.setName(teacher.getName());
        // Bổ sung các thuộc tính khác của giáo viên vào teacherDto (nếu có)
        return teacherDto;
    }
}
