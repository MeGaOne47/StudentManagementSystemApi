package com.example.demo.services;

import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.repository.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private ICourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        return optionalCourse.orElse(null);
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void updateCourse(Course course) {
        courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public Course getCourseByName(String name) {
        return courseRepository.findByName(name);
    }

    public List<Course> searchCourses(String keyword) {
        String searchKeyword = "%" + keyword.toLowerCase() + "%";
        return courseRepository.searchByName(searchKeyword);
    }
}


