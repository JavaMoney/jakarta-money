package org.javamoney.jakarta.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Set;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.javamoney.moneta.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MonetaryAmountMaxTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Mock
	private MonetaryMax monetaryMax;

	private MonetaryAmountMaxValidator monetaryValidator;

	private ConstraintValidatorContext context;

	private MonetaryAmount money;

	@Before
	public void setup(){
		monetaryValidator = new MonetaryAmountMaxValidator();
		money = Money.of(12, Monetary.getCurrency("USD"));
	}

	@Test
	public void shouldReturnsTrueWhenIsNull() {
		Assert.assertTrue(monetaryValidator.isValid(null, context));
	}

	@Test
	public void shouldReturnsTrueWhenIsLesserThanMaximumValue() {
		when(monetaryMax.value()).thenReturn("20.00");
		monetaryValidator.initialize(monetaryMax);
		assertTrue(monetaryValidator.isValid(money, context));
	}

	@Test
	public void shouldReturnsTrueWhenIsEqualsThanMaximumValue() {
		when(monetaryMax.value()).thenReturn("12.00");
		monetaryValidator.initialize(monetaryMax);
		assertTrue(monetaryValidator.isValid(money, context));
	}

	@Test
	public void shouldReturnsFalseWhenIsGreaterThanMinimumValue() {
		when(monetaryMax.value()).thenReturn("11.00");
		monetaryValidator.initialize(monetaryMax);
		assertFalse(monetaryValidator.isValid(money, context));
	}



	@Test
	public void shouldReturnsConstrainsWhenMonetaryIsGreaterThanMaximum() {
		   MonetaryAmountValidator currency = new MonetaryAmountValidator(Money.of(20, Monetary.getCurrency("BRL")));
		   Set<ConstraintViolation<MonetaryAmountValidator>> constraintViolations =
				      validator.validate(currency);

		   assertTrue(constraintViolations.size() == 1);
		   assertEquals("{org.javamoney.midas.constraints.monetaryMax}", constraintViolations.iterator().next().getMessageTemplate());
	}

	@Test
	public void shouldReturnsEmptyConstrainsWhenMonetaryIsNull() {
		   MonetaryAmountValidator currency = new MonetaryAmountValidator(null);
		   Set<ConstraintViolation<MonetaryAmountValidator>> constraintViolations =
				      validator.validate(currency);
		   assertTrue(constraintViolations.isEmpty());
	}

	@Test
	public void shouldReturnsEmptyConstrainsWhenMonetaryIsLesserThanMaximum() {
		   MonetaryAmountValidator currency = new MonetaryAmountValidator(Money.of(10, Monetary.getCurrency("BRL")));
		   Set<ConstraintViolation<MonetaryAmountValidator>> constraintViolations =
				      validator.validate(currency);
		   assertTrue(constraintViolations.isEmpty());
	}
	private class MonetaryAmountValidator {

		@MonetaryMax("10.12")
		private MonetaryAmount money;

		MonetaryAmountValidator(MonetaryAmount money) {
			this.money = money;
		}

	}
}
