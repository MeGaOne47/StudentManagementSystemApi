package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.repository.ICourseRepository;
import com.example.demo.services.CourseService;
import com.example.demo.services.UserTrackingService;
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
    private CourseService courseService;

    @Autowired
    private UserTrackingService userTrackingService;

    @Autowired
    private ICourseRepository courseRepository;

    @GetMapping
    public String showAllCourses(Model model, HttpServletRequest request, Authentication authentication) {

        // Lấy thông tin người dùng và địa chỉ IP
        String username = authentication.getName();
        String ipAddress = request.getRemoteAddr();

        // Lưu thông tin người dùng khi truy cập trang "Teachers"
        userTrackingService.trackUserAccessed(username, ipAddress, "/courses");

        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "course/list";
    }

    @GetMapping("/add")
    public String addCourseForm(Model model, HttpServletRequest request, Authentication authentication) {

        // Lấy thông tin người dùng và địa chỉ IP
        String username = authentication.getName();
        String ipAddress = request.getRemoteAddr();

        // Lưu thông tin người dùng khi truy cập trang "Teachers"
        userTrackingService.trackUserAccessed(username, ipAddress, "/courses/add");

        model.addAttribute("course", new Course());
        return "course/add";
    }

    @PostMapping("/add")
    public String addCourse( @ModelAttribute("course") Course course, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Xử lý lỗi hợp lệ
            return "/course/add";
        }
        courseService.addCourse(course);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm khóa học thành công!");
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Course course = courseService.getCourseById(id);
        if (course == null) {
            model.addAttribute("errorMessage", "Khóa học không tồn tại");
            return "/course/list";
        }
        courseService.deleteCourse(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa khóa học thành công!");
        return "redirect:/courses";
    }

    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable("id") Long id, Model model, HttpServletRequest request, Authentication authentication) {

        // Lấy thông tin người dùng và địa chỉ IP
        String username = authentication.getName();
        String ipAddress = request.getRemoteAddr();

        // Lưu thông tin người dùng khi truy cập trang "Teachers"
        userTrackingService.trackUserAccessed(username, ipAddress, "/courses/edit");

        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (!optionalCourse.isPresent()) {
            return "course/list";
        }
        Course course = optionalCourse.get();
        model.addAttribute("course", course);
        return "course/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCourse(@PathVariable("id") Long id, @ModelAttribute("course") Course course, RedirectAttributes redirectAttributes) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (!optionalCourse.isPresent()) {
            return "redirect:/course/list";
        }
        Course existingCourse = optionalCourse.get();
        existingCourse.setName(course.getName());
//        existingCourse.setDescription(course.getDescription());
        courseRepository.save(existingCourse);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật khóa học thành công!");
        return "redirect:/courses";
    }
}
