package com.example.demo.controller;

import com.example.demo.entity.Assignment;
import com.example.demo.entity.AssignmentForm;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Student;
import com.example.demo.repository.IAssignmentRepository;
import com.example.demo.repository.ICommentRepository;
import com.example.demo.services.AssignmentService;
import com.example.demo.services.CommentService;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final StudentService studentService;
    private final IAssignmentRepository assignmentRepository;
    private final CommentService commentService;

    @Autowired
    private ICommentRepository commentRepository;

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public AssignmentController(AssignmentService assignmentService, StudentService studentService, IAssignmentRepository assignmentRepository, CommentService commentService, JdbcTemplate jdbcTemplate) {
        this.assignmentService = assignmentService;
        this.studentService = studentService;
        this.assignmentRepository = assignmentRepository;
        this.commentService = commentService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public String showAssignments(Model model) {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        model.addAttribute("assignments", assignments);
        List<Comment> comments = commentService.getAllComments();
        model.addAttribute("comments", comments);
        return "assignment/list";
    }



    @GetMapping("/add")
    public String showCreateAssignmentForm(Model model) {
        Assignment assignment = new Assignment();
        model.addAttribute("assignment", assignment);
        return "assignment/add";
    }

    @PostMapping("/add")
    public String createAssignment(@ModelAttribute("assignment") @Valid Assignment assignment, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "assignment/add";
        } else {
            assignmentService.createAssignment(assignment);
            return "redirect:/assignments";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditAssignmentForm(@PathVariable("id") Long id, Model model) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        model.addAttribute("assignment", assignment);
        return "assignment/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateAssignment(@PathVariable("id") Long id, @ModelAttribute("assignment") @Valid Assignment assignment, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "assignment/edit";
        } else {
            Assignment existingAssignment = assignmentService.getAssignmentById(id);
            existingAssignment.setTitle(assignment.getTitle());
            existingAssignment.setDescription(assignment.getDescription());
            // Update other fields of the assignment
            assignmentService.updateAssignment(existingAssignment);
            return "redirect:/assignments";
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAssignment(@PathVariable("id") Long id) {
        assignmentService.deleteAssignment(id);
        return new ResponseEntity<>("Assignment deleted", HttpStatus.OK);
    }

    @GetMapping("/assign/{id}")
    public String showAssignAssignmentForm(@PathVariable("id") Long id, Model model) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        List<Student> students = studentService.getAllStudents();

        AssignmentForm assignForm = new AssignmentForm();
        assignForm.setAssignmentId(id);

        model.addAttribute("assignment", assignment);
        model.addAttribute("assignForm", assignForm);
        model.addAttribute("students", students);

        return "assignment/assign";
    }

    @PostMapping("/assign/{id}")
    public String assignAssignment(
            @PathVariable("id") Long id,
            @ModelAttribute("assignForm") @Valid AssignmentForm assignForm,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("assignment", assignmentService.getAssignmentById(id));
            return "assignment/assign";
        } else {
            Assignment assignment = assignmentService.getAssignmentById(id);
            Long studentId = assignForm.getStudentId();
            Student student = studentService.getStudentById(studentId);
            assignment.setAssignedTo(student);
            assignment.setAssigned(true);
            assignmentService.updateAssignment(assignment);

            return "redirect:/assignments";
        }
    }

    @GetMapping("/submit/{id}")
    public String showSubmitAssignmentForm(@PathVariable("id") Long id, Model model) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        model.addAttribute("assignment", assignment);
        return "assignment/submit";
    }

    @PostMapping("/submit/{id}")
    public String submitAssignment(
            @PathVariable("id") Long id,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid assignment ID"));

        if (!file.isEmpty()) {
            try {
                byte[] fileContent = file.getBytes();

                // Save the file content to the assignment entity
                assignment.setSubmittedFile(fileContent);

                // Update the assignment status
                assignment.setStatus("Submitted");
                assignmentRepository.save(assignment);

                redirectAttributes.addFlashAttribute("successMessage", "Assignment submitted successfully.");
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Failed to submit the assignment.");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "No file selected for submission.");
        }

        return "redirect:/assignments/" + id;
    }


    @PostMapping("/grade/{id}")
    public String gradeAssignment(@PathVariable("id") Long id, @RequestParam("score") int score) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        assignment.setScore(score);
        assignmentService.updateAssignment(assignment);
        return "redirect:/assignments/" + id;
    }

    @PostMapping("/extend/{id}")
    public String extendDeadline(@PathVariable("id") Long id, @RequestParam("deadline") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        assignment.setDeadline(deadline);
        assignmentService.updateAssignment(assignment);
        return "redirect:/assignments/" + id;
    }

    @GetMapping("/{id}")
    public String showAssignmentDetails(@PathVariable("id") Long id, Model model) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        if (assignment == null) {
            throw new IllegalArgumentException("Invalid assignment ID");
        }

        model.addAttribute("assignment", assignment);
        List<Comment> comments = commentRepository.findAll();
        model.addAttribute("comments", comments);

        if (assignment.getStatus() != null && assignment.getStatus().equals("Submitted")) {
            byte[] submittedFile = assignment.getSubmittedFile();
            if (submittedFile != null) {
                String fileContent = new String(submittedFile);
                model.addAttribute("fileContent", fileContent);
            }
        }

        // Lấy thông tin học viên, số điểm và hạn nộp bài
        String studentName = assignment.getStudent().getName();
        Integer score = assignment.getScore(); // Use Integer instead of int to handle null values
        LocalDate deadline = assignment.getDeadline();

        model.addAttribute("studentName", studentName);
        model.addAttribute("score", score != null ? score.intValue() : 0); // Check for null value
        model.addAttribute("deadline", deadline);

        return "assignment/details";
    }
}
