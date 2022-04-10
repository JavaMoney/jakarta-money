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
@Constraint(validatedBy = MonetaryAmountMinValidator.class)
@Documented
/**
 *Informs the minimum value of a {@link MonetaryAmount}.
 *To do the comparison is used the {@link MonetaryAmount#isGreaterThanOrEqualTo(MonetaryAmount)
 * @author Otavio Santana
 */
public @interface MonetaryMin {

	String message() default "{org.javamoney.midas.constraints.monetaryMin}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value();

}