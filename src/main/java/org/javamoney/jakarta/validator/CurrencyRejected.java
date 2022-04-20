/*
 * Copyright (c) 2022, Otavio Santana and others by the @author tag.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

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

/**
 *Informs the currencies that are rejected on validation.
 *This annotation works with {@link javax.money.MonetaryAmount} and {@link javax.money.CurrencyUnit}.
 * @author Otavio Santana
 */
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {CurrencyRejectedValidator.class, MonetaryAmountRejectedValidator.class})
@Documented
public @interface CurrencyRejected {

	String message() default "{org.javamoney.jakarta.validator.currency.rejected}";

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