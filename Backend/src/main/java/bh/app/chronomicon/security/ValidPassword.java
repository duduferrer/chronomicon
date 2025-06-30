package bh.app.chronomicon.security;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint (validatedBy = PasswordValidator.class)
@Target ({ElementType.PARAMETER, ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Senha muito fraca. Utilize no mínimo 8 caracteres, com: 1 MAIÚSCULA, 1 minúscula, 1 número e 1 símbolo";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
