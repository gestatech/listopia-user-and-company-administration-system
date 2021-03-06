package florian_haas.lucas.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;
import javax.validation.constraints.Size;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
@NotBlankString
@Size(min = 1, max = 64)
public @interface ShortComment {
	String message() default "Invalid comment";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
