package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.Teacher;
import com.example.demo.repository.ICourseRepository;
import com.example.demo.repository.ITeacherRepository;
import com.example.demo.services.CourseService;
import com.example.demo.services.TeacherService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ITeacherRepository teacherRepository;

    @Autowired
    private ICourseRepository courseRepository;

    @GetMapping
    public String showAllTeachers(Model model) {
        List<Teacher> teachers = teacherService.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "teacher/list";
    }

    @GetMapping("/add")
    public String addTeacherForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        model.addAttribute("courses", courseService.getAllCourses());
        return "teacher/add";
    }

    @PostMapping("/add")
    public String addTeacher(@Valid @ModelAttribute("teacher") Teacher teacher, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Xử lý lỗi hợp lệ
            return "/teacher/add";
        }
        teacherService.addTeacher(teacher);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm giáo viên thành công!");
        return "redirect:/teachers";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher == null) {
            model.addAttribute("errorMessage", "Giáo viên không tồn tại");
            return "/teacher/list";
        }
        teacherService.deleteTeacher(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa giáo viên thành công!");
        return "redirect:/teachers";
    }

    @GetMapping("/edit/{id}")
    public String editTeacher(@PathVariable("id") Long id, Model model) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (!optionalTeacher.isPresent()) {
            return "teacher/list";
        }
        Teacher teacher = optionalTeacher.get();
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("teacher", teacher);
        model.addAttribute("courses", courses);
        return "teacher/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateTeacher(@PathVariable("id") Long id, @ModelAttribute("teacher") Teacher teacher, RedirectAttributes redirectAttributes) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (!optionalTeacher.isPresent()) {
            return "redirect:/teacher/list";
        }
        Teacher existingTeacher = optionalTeacher.get();
        existingTeacher.setName(teacher.getName());
        existingTeacher.setAge(teacher.getAge());
        existingTeacher.setCourse(teacher.getCourse());
        teacherRepository.save(existingTeacher);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật giáo viên thành công!");
        return "redirect:/teachers";
    }
}

