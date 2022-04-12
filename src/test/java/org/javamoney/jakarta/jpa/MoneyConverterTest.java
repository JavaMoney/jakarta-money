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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.javamoney.midas.javaee7.jpa.MoneyConverter;
import org.javamoney.moneta.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MoneyConverterTest {

	private MoneyConverter converter;

	private CurrencyUnit currency;
	
	private MonetaryAmount expectedMoney;
	
	@Before
	public void setup() {
		converter = new MoneyConverter();
		currency = Monetary.getCurrency("USD");
		expectedMoney = Money.of(10, currency);
	}
	
	@Test
	public void shouldReturnsNullWhenAttributeIsNull() {
		String convertToDatabaseColumn = converter.convertToDatabaseColumn(null);
		assertNull(convertToDatabaseColumn);
	}
	
	@Test
	public void shouldReturnsValueWhenHasAttribute() {
		String convertToDatabaseColumn = converter.convertToDatabaseColumn(expectedMoney);
		assertEquals(expectedMoney.toString(), convertToDatabaseColumn);
	}
	
	@Test
	public void shouldReturnsNullWhenDataBaseAttributeIsNull() {
		String result = converter.convertToDatabaseColumn(null);
		assertNull(result);
	}
	
	@Test
	public void shouldReturnsCurrencyCodeWhenHasInformationFromDataBase() {
		MonetaryAmount moneyConverted = converter.convertToEntityAttribute(expectedMoney.toString());
		Assert.assertTrue(Money.class.isInstance(moneyConverted));
		assertEquals(expectedMoney, moneyConverted);
	}
	
	
}
