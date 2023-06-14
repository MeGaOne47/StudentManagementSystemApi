package com.example.demo.controller;

import com.example.demo.entity.Assignment;
import com.example.demo.entity.Course;
import com.example.demo.repository.IAssignmentRepository;
import com.example.demo.repository.ICourseRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private ICourseRepository courseRepository;

    @Autowired
    private IAssignmentRepository assignmentRepository;

    @GetMapping
    public String showAllCourses(Model model) {
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "course/list";
    }

    @GetMapping("/add")
    public String addCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "course/add";
    }

    @PostMapping("/add")
    public String addCourse(@ModelAttribute("course") Course course, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Xử lý lỗi hợp lệ
            return "/course/add";
        }
        courseRepository.save(course);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm khóa học thành công!");
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (!optionalCourse.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Khóa học không tồn tại");
            return "redirect:/courses";
        }
        Course course = optionalCourse.get();
        courseRepository.delete(course);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa khóa học thành công!");
        return "redirect:/courses";
    }

    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable("id") Long id, Model model) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (!optionalCourse.isPresent()) {
            return "redirect:/courses";
        }
        Course course = optionalCourse.get();
        model.addAttribute("course", course);
        return "course/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCourse(@PathVariable("id") Long id, @ModelAttribute("course") Course course, RedirectAttributes redirectAttributes) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (!optionalCourse.isPresent()) {
            return "redirect:/courses";
        }
        Course existingCourse = optionalCourse.get();
        existingCourse.setName(course.getName());
        courseRepository.save(existingCourse);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật khóa học thành công!");
        return "redirect:/courses";
    }

    @GetMapping("/{courseId}/assignments")
    public String showCourseAssignments(@PathVariable("courseId") Long courseId, Model model) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (!optionalCourse.isPresent()) {
            return "redirect:/courses";
        }
        Course course = optionalCourse.get();
        model.addAttribute("course", course);
        return "course/assignments";
    }

    @GetMapping("/{courseId}/assignments/add")
    public String addAssignmentForm(@PathVariable("courseId") Long courseId, Model model) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (!optionalCourse.isPresent()) {
            return "redirect:/courses";
        }
        Course course = optionalCourse.get();
        model.addAttribute("course", course);
        model.addAttribute("assignment", new Assignment());
        return "course/add-assignment";
    }

    @PostMapping("/{courseId}/assignments/add")
    public String addAssignment(
            @PathVariable("courseId") Long courseId,
            @ModelAttribute("assignment") Assignment assignment,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Xử lý lỗi hợp lệ
            return "/course/add-assignment";
        }
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (!optionalCourse.isPresent()) {
            return "redirect:/courses";
        }
        Course course = optionalCourse.get();
        assignment.setCourse(course);
        assignmentRepository.save(assignment);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm bài tập thành công!");
        return "redirect:/courses/" + courseId + "/assignments";
    }

    @GetMapping("/{courseId}/assignments/delete/{assignmentId}")
    public String deleteAssignment(
            @PathVariable("courseId") Long courseId,
            @PathVariable("assignmentId") Long assignmentId,
            RedirectAttributes redirectAttributes) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (!optionalCourse.isPresent()) {
            return "redirect:/courses";
        }
        Optional<Assignment> optionalAssignment = assignmentRepository.findById(assignmentId);
        if (!optionalAssignment.isPresent()) {
            return "redirect:/courses/" + courseId + "/assignments";
        }
        Assignment assignment = optionalAssignment.get();
        assignmentRepository.delete(assignment);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa bài tập thành công!");
        return "redirect:/courses/" + courseId + "/assignments";
    }
}
