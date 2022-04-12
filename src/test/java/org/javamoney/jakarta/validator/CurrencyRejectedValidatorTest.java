/*
 * Copyright (c) 2002, Otavio Santana and others by the @author tag.
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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyRejectedValidatorTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	private CurrencyRejectedValidator currencyValidator;

	@Mock
	private CurrencyRejected constraintAnnotation;

	@Mock
	private ConstraintValidatorContext context;

	@BeforeEach
	public void setup() {

		currencyValidator = new CurrencyRejectedValidator();
	}


	@Test
	public void shouldReturnsTrueWhenCurrencyIsNull() {
		assertTrue(currencyValidator.isValid(null, context));
	}

	@Test
	public void shouldReturnsFalseWhenCurrencyIsRejected() {
		when(constraintAnnotation.currencies()).thenReturn(new String[0]);
		when(constraintAnnotation.currenciesFromLocales()).thenReturn(new String[0]);
		String currencyCodAllowed = "USD";
		when(constraintAnnotation.currencies()).thenReturn(new String[]{currencyCodAllowed});
		currencyValidator.initialize(constraintAnnotation);
		assertFalse(currencyValidator.isValid(Monetary.getCurrency(currencyCodAllowed), context));
	}

	@Test
	public void shouldReturnsTrueWhenCurrencyAccepted() {
		when(constraintAnnotation.currencies()).thenReturn(new String[0]);
		when(constraintAnnotation.currenciesFromLocales()).thenReturn(new String[0]);
		String currencyCodAllowed = "USD";
		when(constraintAnnotation.currencies()).thenReturn(new String[]{currencyCodAllowed});
		currencyValidator.initialize(constraintAnnotation);
		assertTrue(currencyValidator.isValid(Monetary.getCurrency("EUR"), context));
	}

	  @Test
	   public void shouldReturnsEmptyConstrainsWhenCurrencyIsNull(){
		   CurrencyValidator currency = new CurrencyValidator(null);
		   Set<ConstraintViolation<CurrencyValidator>> constraintViolations =
				      validator.validate(currency);
		   assertTrue(constraintViolations.isEmpty());
	   }

	   @Test
	   public void shouldReturnsEmptyConstrainsWhenCurrencyIsAllowed(){
		   CurrencyValidator currency = new CurrencyValidator(Monetary.getCurrency(Locale.US));
		   Set<ConstraintViolation<CurrencyValidator>> constraintViolations =
				      validator.validate(currency);
		   assertTrue(constraintViolations.isEmpty());
	   }


	   @Test
	   public void shouldReturnsConstrainsWhenCurrencyDenied(){
		   CurrencyValidator currency = new CurrencyValidator(Monetary.getCurrency("BRL"));
		   Set<ConstraintViolation<CurrencyValidator>> constraintViolations =
				      validator.validate(currency);

		   assertTrue(constraintViolations.size() == 1);
		   assertEquals("{org.javamoney.midas.constraints.currencyRejected}", constraintViolations.iterator().next().getMessageTemplate());
	   }

	private class CurrencyValidator {

		@CurrencyRejected(currencies = "BRL")
		private CurrencyUnit currencyUnit;

		CurrencyValidator(CurrencyUnit currencyUnit) {
			this.currencyUnit = currencyUnit;
		}

	}


}
