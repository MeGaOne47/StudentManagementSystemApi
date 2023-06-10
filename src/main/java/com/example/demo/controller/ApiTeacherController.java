package com.example.demo.controller;

import com.example.demo.dto.TeacherDto;
import com.example.demo.entity.Course;
import com.example.demo.entity.Teacher;
import com.example.demo.services.CourseService;
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
@RequestMapping("/api/v1/teachers")
public class ApiTeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    @ResponseBody
    public List<TeacherDto> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        List<TeacherDto> teacherDTOs = new ArrayList<>();
        for (Teacher teacher : teachers) {
            teacherDTOs.add(convertToTeacherDto(teacher));
        }
        return teacherDTOs;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public TeacherDto getTeacherById(@PathVariable Long id) {
        Teacher teacher = teacherService.getTeacherById(id);
        return convertToTeacherDto(teacher);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteTeacher(@PathVariable Long id) {
        if (teacherService.getTeacherById(id) != null)
            teacherService.deleteTeacher(id);
    }

    private TeacherDto convertToTeacherDto(Teacher teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacher.getId());
        teacherDto.setName(teacher.getName());
        // Chuyển đổi kiểu Integer thành String
        String ageString = teacher.getAge().toString();
        teacherDto.setAge(ageString);
        // Chuyển đổi kiểu Course thành tên của Course
        teacherDto.setCourseName(teacher.getCourse().getName());
        return teacherDto;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> addTeacher(@RequestBody TeacherDto teacherDto) {
        try {
            // Chuyển đổi TeacherDto thành Teacher entity
            Teacher teacher = convertToTeacherEntity(teacherDto);
            teacherService.addTeacher(teacher);

            return ResponseEntity.ok("Teacher added successfully!"); // Trả về mã phản hồi HTTP 200 khi thêm thành công
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add teacher."); // Trả về mã phản hồi HTTP 500 nếu có lỗi xảy ra
        }
    }

    private Teacher convertToTeacherEntity(TeacherDto teacherDto) {
        Teacher teacher = new  Teacher();
        teacher.setName(teacherDto.getName());
        teacher.setAge(Integer.parseInt(teacherDto.getAge()));
        Course course = courseService.getCourseByName(teacherDto.getCourseName());
        teacher.setCourse(course);
        return teacher;
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> updateTeacher(@PathVariable Long id, @RequestBody TeacherDto teacherDto) {
        Teacher existingTeacher = teacherService.getTeacherById(id);
        if (existingTeacher != null) {
            Teacher updatedTeacher = convertToTeacherEntity(teacherDto);
            updatedTeacher.setId(existingTeacher.getId());
            teacherService.updateTeacher(updatedTeacher);
            return ResponseEntity.ok().build(); // Trả về mã phản hồi HTTP 200 khi cập nhật thành công
        }
        return ResponseEntity.notFound().build(); // Trả về mã phản hồi HTTP 404 nếu không tìm thấy giáo viên
    }

}
