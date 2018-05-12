package Validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RegisterLoginValid.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RegisterLoginConstraint {
    String message() default "Podany login widnieje już w bazie!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
