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

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MonetaryAmountMinTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Mock
	private MonetaryMin monetaryMin;

	private MonetaryAmountMinValidator monetaryValidator;

	@Mock
	private ConstraintValidatorContext context;

	private MonetaryAmount money;

	@BeforeEach
	public void setup(){
		monetaryValidator = new MonetaryAmountMinValidator();
		money = Money.of(12, Monetary.getCurrency("USD"));
	}

	@Test
	public void shouldReturnsTrueWhenIsNull() {
		assertTrue(monetaryValidator.isValid(null, context));
	}

	@Test
	public void shouldReturnsTrueWhenIsGreatherThanMinimumValue() {
		when(monetaryMin.value()).thenReturn("10.00");
		monetaryValidator.initialize(monetaryMin);
		assertTrue(monetaryValidator.isValid(money, context));
	}

	@Test
	public void shouldReturnsTrueWhenIsEqualsThanMinimumValue() {
		when(monetaryMin.value()).thenReturn("12.00");
		monetaryValidator.initialize(monetaryMin);
		assertTrue(monetaryValidator.isValid(money, context));
	}

	@Test
	public void shouldReturnsFalseWhenIsLesserThanMinimumValue() {
		when(monetaryMin.value()).thenReturn("14.00");
		monetaryValidator.initialize(monetaryMin);
		assertFalse(monetaryValidator.isValid(money, context));
	}



	@Test
	public void shouldReturnsConstrainsWhenMonetaryIsLesserThanMinimum() {
		   MonetaryAmountValidator currency = new MonetaryAmountValidator(Money.of(10,
				   Monetary.getCurrency("BRL")));
		   Set<ConstraintViolation<MonetaryAmountValidator>> constraintViolations =
				      validator.validate(currency);

		   assertTrue(constraintViolations.size() == 1);
		   assertEquals("{org.javamoney.midas.constraints.monetaryMin}",
				   constraintViolations.iterator().next().getMessageTemplate());
	}

	@Test
	public void shouldReturnsEmptyConstrainsWhenMonetaryIsNull() {
		   MonetaryAmountValidator currency = new MonetaryAmountValidator(null);
		   Set<ConstraintViolation<MonetaryAmountValidator>> constraintViolations =
				      validator.validate(currency);
		   assertTrue(constraintViolations.isEmpty());
	}

	@Test
	public void shouldReturnsEmptyConstrainsWhenMonetaryIsGreaterThanMimimum() {
		   MonetaryAmountValidator currency = new MonetaryAmountValidator(
				   Money.of(20, Monetary.getCurrency("BRL")));
		   Set<ConstraintViolation<MonetaryAmountValidator>> constraintViolations =
				      validator.validate(currency);

		   assertTrue(constraintViolations.isEmpty());
	}
	private static class MonetaryAmountValidator {

		@MonetaryMin("10.12")
		private MonetaryAmount money;

		MonetaryAmountValidator(MonetaryAmount money) {
			this.money = money;
		}

	}
}
