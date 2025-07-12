package bh.app.chronomicon.security;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint (validatedBy = RoleValidator.class)
@Target ({ElementType.PARAMETER, ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
public @interface ValidRole {
    String message() default "Você não tem permissão para adicionar um usuário com este nível de privilégio";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
