package com.nnk.springboot.password_validation;

import com.nnk.springboot.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import javax.validation.*;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(MockitoExtension.class)
class PasswordConstraintsValidatorTest {

    private static Validator validator;
    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

    }
    @Test
    public void testInvalidPassword() {
        User user = new User();

        user.setUsername("newUserName");
        user.setPassword("12345");
        user.setFullname("userFullName");
        user.setRole("USER");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertThat(constraintViolations.size()).isEqualTo(1);

    }

    @Test
    public void testValidPasswords() {
        User user = new User();

        user.setUsername("newUserName");
        user.setPassword("Subhy7!");
        user.setFullname("userFullName");
        user.setRole("USER");


        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertThat(constraintViolations.size()).isEqualTo(0);
    }


    @Test
    public void testInvalidPassword_whenTooShort() {
        User user = new User();

        user.setUsername("newUserName");
        user.setPassword("12");
        user.setFullname("userFullName");
        user.setRole("USER");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertThat(constraintViolations.size()).isEqualTo(1);

    }


}