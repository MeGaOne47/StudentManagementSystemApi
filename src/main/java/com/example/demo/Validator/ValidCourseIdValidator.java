package com.example.demo.Validator;

import com.example.demo.entity.Course;
import com.example.demo.Validator.anotation.ValidCourseId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidCourseIdValidator implements ConstraintValidator<ValidCourseId, Course> {
    @Override
    public boolean isValid (Course course, ConstraintValidatorContext context) {
        return course != null && course.getId() != null;
    }
}
