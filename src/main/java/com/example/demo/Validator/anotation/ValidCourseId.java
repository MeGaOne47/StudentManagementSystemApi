package com.example.demo.Validator.anotation;

import com.example.demo.Validator.ValidCourseIdValidator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidCourseIdValidator.class)
@Documented
public @interface ValidCourseId {
    String message() default "Invalid Course ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

