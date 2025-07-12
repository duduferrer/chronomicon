package bh.app.chronomicon.security;

import bh.app.chronomicon.model.enums.Role;
import bh.app.chronomicon.service.AuthService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleValidator implements ConstraintValidator<ValidRole, Role> {
	@Autowired
	AuthService authService;
	@Override
	public void initialize(ValidRole constraintAnnotation) {
		ConstraintValidator.super.initialize (constraintAnnotation);
	}
	
	@Override
	public boolean isValid(Role settedRole, ConstraintValidatorContext constraintValidatorContext) {
		if(settedRole==null) return false;
		return authService.getAuthenticatedUserRole().isHigherThan(settedRole);
	}

}
