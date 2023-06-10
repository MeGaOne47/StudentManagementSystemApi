package com.example.demo.Validator;
import com.example.demo.repository.IUserRepository;
import com.example.demo.Validator.anotation.ValidUsername;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidUsernameValidator implements ConstraintValidator<ValidUsername, String> {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (userRepository == null)
            return true;
        return userRepository.findByUsername(username) == null;
    }
}
