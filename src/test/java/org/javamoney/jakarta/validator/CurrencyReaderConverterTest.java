package org.javamoney.jakarta.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrencyReaderConverterTest {


	@Mock
	private CurrencyAccepted currency;

	@BeforeEach
	public void setup() {
		when(currency.currencies()).thenReturn(new String[0]);
		when(currency.currenciesFromLocales()).thenReturn(new String[0]);

	}

	@Test
	public void shouldReturnsEmptyAcceptedAndRejectedListWhenCurrencyCodeIsEmpty(){
		CurrencyReaderConverter converter = new CurrencyReaderConverter(currency);
		assertTrue(converter.getCurrencies().isEmpty());
	}

	@Test
	public void shouldReturnsOneElementAcceptedAndRejectedListWhenCurrencyCodeHasOneElement(){
		when(currency.currencies()).thenReturn(new String[]{"BRL"});

		CurrencyReaderConverter converter = new CurrencyReaderConverter(currency);
		assertFalse(converter.getCurrencies().isEmpty());

	}

	@Test
	public void shouldReturnsElementsAcceptedAndRejectedListWhenCurrencyCodeHasElements(){
		when(currency.currencies()).thenReturn(new String[]{"BRL","USD"});

		CurrencyReaderConverter converter = new CurrencyReaderConverter(currency);
		assertFalse(converter.getCurrencies().isEmpty());
//
//		assertThat(converter.getCurrencies().toArray(),
//				Matchers.arrayContaining(Monetary.getCurrency("BRL"), Monetary.getCurrency("USD")));
	}


	@Test
	public void shouldReturnsEmptyAcceptedAndRejectedListWhenLocaleIsEmpty(){

		CurrencyReaderConverter converter = new CurrencyReaderConverter(currency);
		assertTrue(converter.getCurrencies().isEmpty());
	}

	@Test
	public void shouldReturnsOneElementAcceptedAndRejectedListWhenLocaleHasOneElement(){

		when(currency.currenciesFromLocales()).thenReturn(new String[]{"en_US"});

		CurrencyReaderConverter converter = new CurrencyReaderConverter(currency);
		assertFalse(converter.getCurrencies().isEmpty());

//		assertThat(converter.getCurrencies().toArray(),
//				Matchers.arrayContaining(Monetary.getCurrency(Locale.US)));
	}

	@Test
	public void shouldReturnsElementsAcceptedAndRejectedListWhenLocaleHasElements(){

		when(currency.currenciesFromLocales()).thenReturn(new String[]{"en_US","en_GB"});

		CurrencyReaderConverter converter = new CurrencyReaderConverter(currency);
		assertFalse(converter.getCurrencies().isEmpty());

//		assertThat(converter.getCurrencies().toArray(),
//				Matchers.arrayContaining(Monetary.getCurrency(Locale.US), Monetary.getCurrency(Locale.UK)));
	}

	@Test
	public void shouldReturnsErrorOnElementsAcceptedWhenLocaleIsWrong(){
		Assertions.assertThrows(IllegalArgumentException.class, () -> new CurrencyReaderConverter(currency));
		when(currency.currenciesFromLocales()).thenReturn(new String[]{"en"});
	}

}
