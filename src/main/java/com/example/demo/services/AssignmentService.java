package com.example.demo.services;

import com.example.demo.entity.Assignment;
import com.example.demo.entity.AssignmentSubmission;
import com.example.demo.entity.Comment;
import com.example.demo.repository.IAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {
    private final IAssignmentRepository assignmentRepository;

    @Autowired
    public AssignmentService(IAssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment getAssignmentById(Long id) {
        Optional<Assignment> assignment = assignmentRepository.findById(id);
        return assignment.orElse(null);
    }

    public Assignment createAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public Assignment updateAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }

    public AssignmentSubmission submitAssignment(Long assignmentId, AssignmentSubmission submission) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.setSubmission(submission);
        return assignmentRepository.save(assignment).getSubmission();
    }

    public AssignmentSubmission getAssignmentSubmission(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        return assignment.getSubmission();
    }

    public Comment addComment(Long assignmentId, Comment comment) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        comment.setAssignment(assignment);
        // Set other fields of the comment if needed

        assignment.getComments().add(comment);
        assignmentRepository.save(assignment);
        return comment;
    }

    public void save(Assignment assignment) {
        assignmentRepository.save(assignment);
    }

    public Assignment findById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));
    }
}
