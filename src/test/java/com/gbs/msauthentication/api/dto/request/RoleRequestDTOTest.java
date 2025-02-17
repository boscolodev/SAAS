package com.gbs.msauthentication.api.dto.request;

import com.gbs.msauthentication.DTOFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoleRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidRoleRequestDTO() {
        RoleRequestDTO request = DTOFactory.createValidRoleRequestDTO();
        Set<ConstraintViolation<RoleRequestDTO>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidRoleRequestDTO() {
        RoleRequestDTO request = DTOFactory.createInvalidRoleRequestDTO();
        Set<ConstraintViolation<RoleRequestDTO>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }
}