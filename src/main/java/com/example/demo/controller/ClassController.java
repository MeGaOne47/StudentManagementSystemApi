package com.example.demo.controller;

import com.example.demo.dto.ClassDto;
import com.example.demo.entity.ClassEntity;
import com.example.demo.services.ClassService;
import com.example.demo.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/classes")
public class ClassController {
    @Autowired
    private ClassService classService;

    @Autowired
    private TeacherService teacherService;
    @GetMapping
    public String showAllClasses(Model model) {
        List<ClassEntity> classes = classService.getAllClasses();
        model.addAttribute("classes", classes);
        return "class/list";
    }

    @GetMapping("/add")
    public String addClassForm(Model model) {
        model.addAttribute("classDto", new ClassDto());
        model.addAttribute("teachers", teacherService.getAllTeachers()); // Thêm dữ liệu giáo viên
        return "class/add";
    }


    @PostMapping("/add")
    public String addClass(@Valid @ModelAttribute("classDto") ClassDto classDto, BindingResult result) {
        if (result.hasErrors()) {
            // Xử lý lỗi hợp lệ
            return "class/add";
        }
        ClassEntity classEntity = convertToClassEntity(classDto);
        classService.addClass(classEntity);
        return "redirect:/classes";
    }

    @GetMapping("/edit/{id}")
    public String editClassForm(@PathVariable("id") Long id, Model model) {
        ClassEntity classEntity = classService.getClassById(id);
        if (classEntity == null) {
            return "redirect:/classes";
        }
        ClassDto classDto = convertToClassDto(classEntity);
        model.addAttribute("classDto", classDto);
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "class/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateClass(@PathVariable("id") Long id, @Valid @ModelAttribute("classDto") ClassDto classDto, BindingResult result) {
        if (result.hasErrors()) {
            // Xử lý lỗi hợp lệ
            return "class/edit";
        }
        ClassEntity classEntity = convertToClassEntity(classDto);
        classEntity.setId(id);
        classService.updateClass(classEntity);
        return "redirect:/classes";
    }

    @GetMapping("/delete/{id}")
    public String deleteClass(@PathVariable("id") Long id) {
        classService.deleteClass(id);
        return "redirect:/classes";
    }

    private ClassDto convertToClassDto(ClassEntity classEntity) {
        ClassDto classDto = new ClassDto();
        classDto.setId(classEntity.getId());
        classDto.setName(classEntity.getClassName());
        classDto.setDescription(classEntity.getClassDescription());
        return classDto;
    }

    private ClassEntity convertToClassEntity(ClassDto classDto) {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setClassName(classDto.getName());
        classEntity.setClassDescription(classDto.getDescription());
        return classEntity;
    }
}
