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

	}

	@Test
	public void shouldReturnsElementsAcceptedAndRejectedListWhenLocaleHasElements(){

		when(currency.currenciesFromLocales()).thenReturn(new String[]{"en_US","en_GB"});

		CurrencyReaderConverter converter = new CurrencyReaderConverter(currency);
		assertFalse(converter.getCurrencies().isEmpty());

	}

	@Test
	public void shouldReturnsErrorOnElementsAcceptedWhenLocaleIsWrong(){
		when(currency.currenciesFromLocales()).thenReturn(new String[]{"en"});
		Assertions.assertThrows(IllegalArgumentException.class, () -> new CurrencyReaderConverter(currency));
	}

}
