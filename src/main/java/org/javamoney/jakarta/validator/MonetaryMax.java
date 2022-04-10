package org.javamoney.jakarta.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = MonetaryAmountMaxValidator.class)
@Documented
/**
 *Informs the maximum value of a {@link MonetaryAmount}.
 *To do the comparison is used the {@link MonetaryAmount#isLessThanOrEqualTo(MonetaryAmount)
 * @author Otavio Santana
 */
public @interface MonetaryMax {

	String message() default "{org.javamoney.midas.constraints.monetaryMax}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value();

}