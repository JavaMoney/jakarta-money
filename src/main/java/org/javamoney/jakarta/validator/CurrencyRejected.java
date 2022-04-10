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
@Constraint(validatedBy = {CurrencyRejectedValidator.class, MonetaryAmountRejectedValidator.class})
@Documented
/**
 *Informs the currencies that are rejected on validation.
 *This annotation works with MonetaryAmount and CurrencyUnit.
 * @author Otavio Santana
 */
public @interface CurrencyRejected {

	String message() default "{org.javamoney.midas.constraints.currencyRejected}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Informs the currency code.
     * Ex: 'USD', 'BRL', 'EUR'
     * @return currencies rejected using the currency code
     */
    String[] currencies() default "";
    /**
     * Informs the Locale where the currencies are from.
     * Ex: 'en_US','pt_BR', 'en_GB'
     * @return currencies rejected using the currency code
     */
    String[] currenciesFromLocales() default "";
}