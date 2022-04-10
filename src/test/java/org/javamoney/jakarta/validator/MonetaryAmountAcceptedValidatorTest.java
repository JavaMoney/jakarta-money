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
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MonetaryAmountAcceptedValidatorTest {


	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	private MonetaryAmountAcceptedValidator monetaryAmountValidator;

	@Mock
	private CurrencyAccepted constraintAnnotation;

	private ConstraintValidatorContext context = null;

	@BeforeEach
	public void setup() {
		monetaryAmountValidator = new MonetaryAmountAcceptedValidator();
	}


	@Test
	public void shouldReturnsTrueWhenCurrecyIsNull() {
		assertTrue(monetaryAmountValidator.isValid(null, context));
	}

	@Test
	public void shouldReturnsTrueWhenCurrencyIsAllowed() {
		when(constraintAnnotation.currencies()).thenReturn(new String[0]);
		when(constraintAnnotation.currenciesFromLocales()).thenReturn(new String[0]);
		String currencyCodAllowed = "USD";
		when(constraintAnnotation.currencies()).thenReturn(new String[]{currencyCodAllowed});
		monetaryAmountValidator.initialize(constraintAnnotation);

		assertTrue(monetaryAmountValidator.isValid(Money.of(10,Monetary.getCurrency(currencyCodAllowed)), context));
	}

	@Test
	public void shouldReturnsFalseWhenCurrencyIsDenied() {
		when(constraintAnnotation.currencies()).thenReturn(new String[0]);
		when(constraintAnnotation.currenciesFromLocales()).thenReturn(new String[0]);
		String currencyCodAllowed = "USD";
		when(constraintAnnotation.currencies()).thenReturn(new String[]{currencyCodAllowed});
		monetaryAmountValidator.initialize(constraintAnnotation);
		assertFalse(monetaryAmountValidator.isValid(Money.of(10, Monetary.getCurrency("EUR")), context));
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

		   MonetaryAmountValidator currency = new MonetaryAmountValidator(Money.of(10, Monetary.getCurrency("BRL")));
		   Set<ConstraintViolation<MonetaryAmountValidator>> constraintViolations =
				      validator.validate(currency);
		   assertTrue(constraintViolations.isEmpty());
	   }


	   @Test
	   public void shouldReturnsConstrainsWhenCurrencyDenied(){

		   MonetaryAmountValidator currency = new MonetaryAmountValidator(Money.of(10, Monetary.getCurrency(Locale.US)));
		   Set<ConstraintViolation<MonetaryAmountValidator>> constraintViolations =
				      validator.validate(currency);

		   assertTrue(constraintViolations.size() == 1);
		   assertEquals("{org.javamoney.midas.constraints.currencyAccepted}", constraintViolations.iterator().next().getMessageTemplate());
	   }

	private class MonetaryAmountValidator {

		@CurrencyAccepted(currencies = "BRL")
		private MonetaryAmount money;

		MonetaryAmountValidator(MonetaryAmount money) {
			this.money = money;
		}

	}

}
