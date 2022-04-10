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
public class CurrencyAcceptedValidatorTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	private CurrencyAcceptedValidator currencyValidator;

	@Mock
	private CurrencyAccepted constraintAnnotation;

	private ConstraintValidatorContext context;

	@BeforeEach
	public void setup() {

		currencyValidator = new CurrencyAcceptedValidator();
	}


	@Test
	public void shouldReturnsTrueWhenCurrecyIsNull() {
		assertTrue(currencyValidator.isValid(null, context));
	}

	@Test
	public void shouldReturnsTrueWhenCurrencyIsAllowed() {
		when(constraintAnnotation.currencies()).thenReturn(new String[0]);
		when(constraintAnnotation.currenciesFromLocales()).thenReturn(new String[0]);
		String currencyCodAllowed = "USD";
		when(constraintAnnotation.currencies()).thenReturn(new String[]{currencyCodAllowed});
		currencyValidator.initialize(constraintAnnotation);
		assertTrue(currencyValidator.isValid(Monetary.getCurrency(currencyCodAllowed), context));
	}

	@Test
	public void shouldReturnsFalseWhenCurrencyIsDenied() {
		when(constraintAnnotation.currencies()).thenReturn(new String[0]);
		when(constraintAnnotation.currenciesFromLocales()).thenReturn(new String[0]);
		String currencyCodAllowed = "USD";
		when(constraintAnnotation.currencies()).thenReturn(new String[]{currencyCodAllowed});
		currencyValidator.initialize(constraintAnnotation);
		assertFalse(currencyValidator.isValid(Monetary.getCurrency("EUR"), context));
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
		   CurrencyValidator currency = new CurrencyValidator(Monetary.getCurrency("BRL"));
		   Set<ConstraintViolation<CurrencyValidator>> constraintViolations =
				      validator.validate(currency);
		   assertTrue(constraintViolations.isEmpty());
	   }


	   @Test
	   public void shouldReturnsConstrainsWhenCurrencyDenied(){
		   CurrencyValidator currency = new CurrencyValidator(Monetary.getCurrency(Locale.US));
		   Set<ConstraintViolation<CurrencyValidator>> constraintViolations =
				      validator.validate(currency);

		   assertTrue(constraintViolations.size() == 1);
		   assertEquals("{org.javamoney.midas.constraints.currencyAccepted}", constraintViolations.iterator().next().getMessageTemplate());
	   }

	private class CurrencyValidator {

		@CurrencyAccepted(currencies = "BRL")
		private CurrencyUnit currencyUnit;

		CurrencyValidator(CurrencyUnit currencyUnit) {
			this.currencyUnit = currencyUnit;
		}

	}


}
