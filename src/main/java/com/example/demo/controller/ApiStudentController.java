package com.example.demo.controller;

import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.services.CourseService;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/students")
public class ApiStudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    @ResponseBody
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        List<StudentDto> studentDTOs = new ArrayList<>();
        for (Student student : students) {
            studentDTOs.add(convertToStudentDto(student));
        }
        return studentDTOs;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public StudentDto getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return convertToStudentDto(student);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteStudent(@PathVariable Long id) {
        if (studentService.getStudentById(id) != null)
            studentService.deleteStudent(id);
    }

    private StudentDto convertToStudentDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        // Chuyển đổi kiểu Integer thành String
        String ageString = student.getAge().toString();
        studentDto.setAge(ageString);
//        studentDto.setCourseName(courseService.getCourseById(student.getCourse().getId()).getName());
        studentDto.setCourseName(student.getCourseName());
        return studentDto;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> addStudent(@RequestBody StudentDto studentDto) {
        try {
            // Chuyển đổi StudentDto thành Student entity
            Student student = convertToStudentEntity(studentDto);
            studentService.addStudent(student);

            return ResponseEntity.ok("Student added successfully!"); // Trả về mã phản hồi HTTP 200 khi thêm thành công
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add student."); // Trả về mã phản hồi HTTP 500 nếu có lỗi xảy ra
        }
    }

    private Student convertToStudentEntity(StudentDto studentDto) {
        Student student = new  Student();
        student.setName(studentDto.getName());
        student.setAge(Integer.parseInt(studentDto.getAge()));
        Course course = courseService.getCourseByName(studentDto.getCourseName());
        student.setCourse(course);
        return student;
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent != null) {
            Student updatedStudent = convertToStudentEntity(studentDto);
            updatedStudent.setId(existingStudent.getId());
            studentService.updateStudent(updatedStudent);
            return ResponseEntity.ok().build(); // Trả về mã phản hồi HTTP 200 khi cập nhật thành công
        }
        return ResponseEntity.notFound().build(); // Trả về mã phản hồi HTTP 404 nếu không tìm thấy học viên
    }

}
