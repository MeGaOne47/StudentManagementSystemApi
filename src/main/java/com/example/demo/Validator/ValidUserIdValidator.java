package com.example.demo.Validator;

import com.example.demo.Validator.anotation.ValidUserId;
import com.example.demo.entity.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ValidUserIdValidator implements ConstraintValidator<ValidUserId, User> {
    @Override
    public boolean isValid (User user, ConstraintValidatorContext context) {
        if(user == null)
        {
            return true;
        }
        return user.getId() != null;
    }
}
