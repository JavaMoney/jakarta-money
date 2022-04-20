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
package org.javamoney.jakarta.jsf;

import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.money.MonetaryAmount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FastMoneyConverterTest {

private FastMoneyConverter converter;
	
	private FacesContext context;
	
	private UIComponent component;
	
	private final String monetaryValue = "BRL 10";
	
	@BeforeEach
	public void init() {
		converter = new FastMoneyConverter();
	}
	
	@Test
	public void shouldReturnsNullWhenStringIsNull() {
		Object result = converter.getAsObject(context, component, null);
		assertNull(result);
	}
	
	@Test
	public void shouldReturnsCurrencyWhenHasCurrencyCode() {
		Object result = converter.getAsObject(context, component, monetaryValue);
		assertNotNull(result);
		FastMoney currency = FastMoney.class.cast(result);
		assertEquals(FastMoney.parse(monetaryValue), currency);
	}
	
	@Test
	public void shouldReturnsNullWhenObjectIsNull() {
		Object result = converter.getAsString(context, component, null);
		assertNull(result);
	}
	
	@Test
	public void shouldReturnsCurrencyCodeWhenHasCurrency() {
		FastMoney expectedMoney = FastMoney.parse(monetaryValue);
		String result = converter.getAsString(context, component, expectedMoney);
		assertNotNull(result);
		assertEquals(expectedMoney.toString(), result);
	}
}
