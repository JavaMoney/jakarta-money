package org.javamoney.jakarta.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Locale;

import javax.money.Monetary;

import org.hamcrest.Matchers;
import org.javamoney.midas.javaee7.validator.CurrencyAccepted;
import org.javamoney.midas.javaee7.validator.CurrencyReaderConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyReaderConverterTest {


	@Mock
	private CurrencyAccepted currency;

	@Before
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

//		assertThat(converter.getCurrencies().toArray(),
//				Matchers.arrayContaining(Monetary.getCurrency("BRL")));
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

	@Test(expected = IllegalArgumentException.class)
	public void shouldReturnsErrorOnElementsAcceptedWhenLocaleIsWrong(){
		when(currency.currenciesFromLocales()).thenReturn(new String[]{"en"});
		new CurrencyReaderConverter(currency);
	}

}
