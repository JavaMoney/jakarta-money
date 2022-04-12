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

package org.javamoney.jakarta.jpa;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class CurrencyConverterTest {
	
	private CurrencyConverter currencyConverter;
	
	private CurrencyUnit currencyUnit = Monetary.getCurrency("BRL");
	
	@BeforeEach
	public void setup() {
		currencyConverter = new CurrencyConverter();
	}
	
	@Test
	public void shouldReturnsNullWhenTheAttributeIsNull() {
		String result = currencyConverter.convertToDatabaseColumn(null);
		assertNull(result);
	}
	
	@Test
	public void shouldReturnsTheCurrentCodeWhenHasAttribute() {
		
		String result = currencyConverter.convertToDatabaseColumn(currencyUnit);
		assertEquals(currencyUnit.toString(), result);
	}
	
	@Test
	public void shouldReturnsNullWhenDataBaseAttributeIsNull() {
		String result = currencyConverter.convertToDatabaseColumn(null);
		assertNull(result);
	}
	
	@Test
	public void shouldReturnsCurrencyCodeWhenHasInformationFromDataBase() {
		CurrencyUnit currencyConverted = currencyConverter.convertToEntityAttribute(currencyUnit.toString());
		assertEquals(currencyUnit, currencyConverted);
	}


}
