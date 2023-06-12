package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.entity.Course;
import com.example.demo.repository.IStudentRepository;
import com.example.demo.repository.ICourseRepository;
import com.example.demo.services.StudentService;
import com.example.demo.services.CourseService;
import javax.validation.Valid;

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
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService sourceService;

    @Autowired
    private UserTrackingService userTrackingService;

    @Autowired
    private IStudentRepository iStudentRepository;

    @Autowired
    private ICourseRepository iCourseRepository;

    @GetMapping
    public String showAllStudents(Model model, HttpServletRequest request, Authentication authentication) {

        // Lấy thông tin người dùng và địa chỉ IP
        String username = authentication.getName();
        String ipAddress = request.getRemoteAddr();

        // Lưu thông tin người dùng khi truy cập trang "Teachers"
        userTrackingService.trackUserAccessed(username, ipAddress, "/students");

        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "student/list";
    }

    @GetMapping("/add")
    public String addStudentForm(Model model, HttpServletRequest request, Authentication authentication) {

        // Lấy thông tin người dùng và địa chỉ IP
        String username = authentication.getName();
        String ipAddress = request.getRemoteAddr();

        // Lưu thông tin người dùng khi truy cập trang "Teachers"
        userTrackingService.trackUserAccessed(username, ipAddress, "/students/add");

        model.addAttribute("student", new Student());
        model.addAttribute("courses", sourceService.getAllCourses());
        return "student/add";
    }

    @PostMapping("/add")
    public String addStudent(@Valid @ModelAttribute("student") Student student, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Xử lý lỗi hợp lệ
            return "/student/add";
        }
        studentService.addStudent(student);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm học viên thành công!");
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            model.addAttribute("errorMessage", "Học viên không tồn tại");
            return "/student/list";
        }
        studentService.deleteStudent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa học viên thành công!");
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable("id") Long id, Model model, HttpServletRequest request, Authentication authentication) {

        // Lấy thông tin người dùng và địa chỉ IP
        String username = authentication.getName();
        String ipAddress = request.getRemoteAddr();

        // Lưu thông tin người dùng khi truy cập trang "Teachers"
        userTrackingService.trackUserAccessed(username, ipAddress, "/students/edit");

        Optional<Student> optionalStudent = iStudentRepository.findById(id);
        if (!optionalStudent.isPresent()) {
            return "student/list";
        }
        Student student = optionalStudent.get();
        List<Course> courses = iCourseRepository.findAll();
        model.addAttribute("student", student);
        model.addAttribute("courses", courses);
        return "student/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable("id") Long id, @ModelAttribute("student") Student student, RedirectAttributes redirectAttributes) {
        Optional<Student> optionalStudent = iStudentRepository.findById(id);
        if (!optionalStudent.isPresent()) {
            return "redirect:/student/list";
        }
        Student existingStudent = optionalStudent.get();
        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setCourse(student.getCourse());
        iStudentRepository.save(existingStudent);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật học viên thành công!");
        return "redirect:/students";
    }
}
