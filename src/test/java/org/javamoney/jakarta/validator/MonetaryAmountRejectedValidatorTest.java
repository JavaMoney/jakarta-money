package org.javamoney.jakarta.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Locale;
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
public class MonetaryAmountRejectedValidatorTest {


	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	private MonetaryAmountRejectedValidator monetaryAmountValidator;

	@Mock
	private CurrencyRejected constraintAnnotation;

	private ConstraintValidatorContext context;

	@Before
	public void setup() {
		when(constraintAnnotation.currencies()).thenReturn(new String[0]);
		when(constraintAnnotation.currenciesFromLocales()).thenReturn(new String[0]);
		monetaryAmountValidator = new MonetaryAmountRejectedValidator();
	}


	@Test
	public void shouldReturnsTrueWhenCurrecyIsNull() {
		Assert.assertTrue(monetaryAmountValidator.isValid(null, context));
	}

	@Test
	public void shouldReturnsFalseWhenCurrencyIsRejected() {
		String currencyCodAllowed = "USD";
		when(constraintAnnotation.currencies()).thenReturn(new String[]{currencyCodAllowed});
		monetaryAmountValidator.initialize(constraintAnnotation);

		assertFalse(monetaryAmountValidator.isValid(Money.of(10,Monetary.getCurrency(currencyCodAllowed)), context));
	}

	@Test
	public void shouldReturnsTrueWhenCurrencyIsAccepted() {
		String currencyCodAllowed = "USD";
		when(constraintAnnotation.currencies()).thenReturn(new String[]{currencyCodAllowed});
		monetaryAmountValidator.initialize(constraintAnnotation);
		assertTrue(monetaryAmountValidator.isValid(Money.of(10, Monetary.getCurrency("EUR")), context));
	}

	@Test
	   public void shouldReturnsEmptyConstrainsWhenCurrencyIsNull(){
		  MonetaryAmountValidator currency = new MonetaryAmountValidator(null);
		   Set<ConstraintViolation<MonetaryAmountValidator>> constraintViolations =
				      validator.validate(currency);
		   assertTrue(constraintViolations.isEmpty());
	   }

	   @Test
	   public void shouldReturnsEmptyConstrainsWhenCurrencyIsAllowed(){

		   MonetaryAmountValidator currency = new MonetaryAmountValidator(Money.of(10, Monetary.getCurrency(Locale.US)));
		   Set<ConstraintViolation<MonetaryAmountValidator>> constraintViolations =
				      validator.validate(currency);
		   assertTrue(constraintViolations.isEmpty());
	   }


	   @Test
	   public void shouldReturnsConstrainsWhenCurrencyDenied(){

		   MonetaryAmountValidator currency = new MonetaryAmountValidator(Money.of(10, Monetary.getCurrency("BRL")));
		   Set<ConstraintViolation<MonetaryAmountValidator>> constraintViolations =
				      validator.validate(currency);

		   assertTrue(constraintViolations.size() == 1);
		   assertEquals("{org.javamoney.midas.constraints.currencyRejected}", constraintViolations.iterator().next().getMessageTemplate());
	   }

	private class MonetaryAmountValidator {

		@CurrencyRejected(currencies = "BRL")
		private MonetaryAmount money;

		MonetaryAmountValidator(MonetaryAmount money) {
			this.money = money;
		}

	}

}
