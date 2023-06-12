package com.example.demo.controller;

import com.example.demo.dto.CourseDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/courses")
public class ApiCourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        List<CourseDto> courseDtos = courses.stream()
                .map(this::convertToCourseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        CourseDto courseDto = convertToCourseDto(course);
        return ResponseEntity.ok(courseDto);
    }

    @PostMapping
    public ResponseEntity<CourseDto> addCourse(@RequestBody CourseDto courseDto) {
        Course course = convertToCourseEntity(courseDto);
        courseService.addCourse(course);
        courseDto.setId(course.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        Course existingCourse = courseService.getCourseById(id);
        if (existingCourse == null) {
            return ResponseEntity.notFound().build();
        }
        Course updatedCourse = convertToCourseEntity(courseDto);
        updatedCourse.setId(existingCourse.getId());
        courseService.updateCourse(updatedCourse);
        return ResponseEntity.ok(courseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    private CourseDto convertToCourseDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setId(course.getId());
        courseDto.setName(course.getName());
        courseDto.setDescription(course.getDescription()); // Thêm trường 'description'

        return courseDto;
    }

    private Course convertToCourseEntity(CourseDto courseDto) {
        Course course = new Course();
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription()); // Thêm trường 'description'
        return course;
    }

    @GetMapping("/search")
    public List<CourseDto> searchCourses(@RequestParam("keyword") String keyword) {
        List<Course> courses = courseService.searchCourses(keyword);
        List<CourseDto> courseDTOs = new ArrayList<>();
        for (Course course : courses) {
            courseDTOs.add(convertToCourseDto(course));
        }
        return courseDTOs;
    }
}
