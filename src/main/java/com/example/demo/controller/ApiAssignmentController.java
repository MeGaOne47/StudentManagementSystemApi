package com.example.demo.controller;

import com.example.demo.dto.AssignmentDto;
import com.example.demo.entity.Assignment;
import com.example.demo.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/assignments")
public class ApiAssignmentController {
    private final AssignmentService assignmentService;

    @Autowired
    public ApiAssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public ResponseEntity<List<AssignmentDto>> getAllAssignments() {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        List<AssignmentDto> assignmentDtos = assignments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(assignmentDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDto> getAssignmentById(@PathVariable("id") Long id) {
        Assignment assignment = assignmentService.getAssignmentById(id);
        if (assignment != null) {
            AssignmentDto assignmentDto = convertToDto(assignment);
            return ResponseEntity.ok(assignmentDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<AssignmentDto> createAssignment(@RequestBody AssignmentDto assignmentDto) {
        Assignment assignment = convertToEntity(assignmentDto);
        Assignment createdAssignment = assignmentService.createAssignment(assignment);
        AssignmentDto createdAssignmentDto = convertToDto(createdAssignment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssignmentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentDto> updateAssignment(@PathVariable("id") Long id, @RequestBody AssignmentDto assignmentDto) {
        Assignment existingAssignment = assignmentService.getAssignmentById(id);
        if (existingAssignment != null) {
            Assignment updatedAssignment = convertToEntity(assignmentDto);
            updatedAssignment.setId(existingAssignment.getId());
            Assignment savedAssignment = assignmentService.updateAssignment(updatedAssignment);
            AssignmentDto savedAssignmentDto = convertToDto(savedAssignment);
            return ResponseEntity.ok(savedAssignmentDto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable("id") Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }



    // Utility methods for conversion between Entity and DTO

    private AssignmentDto convertToDto(Assignment assignment) {
        AssignmentDto dto = new AssignmentDto();
        dto.setId(assignment.getId());
        dto.setTitle(assignment.getTitle());
        dto.setDescription(assignment.getDescription());
        // Set other fields of the DTO as needed
        return dto;
    }

    private Assignment convertToEntity(AssignmentDto dto) {
        Assignment assignment = new Assignment();
        assignment.setTitle(dto.getTitle());
        assignment.setDescription(dto.getDescription());
        // Set other fields of the Entity as needed
        return assignment;
    }
}
