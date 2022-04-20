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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.money.CurrencyUnit;
import javax.money.Monetary;

/**
 * Converter to {@link CurrencyUnit} it serializes to {@link String} using the
 * currency code.
 * 
 * @author Otavio Santana
 * @author Werner Keil
 */
@FacesConverter(" org.javamoney.jakarta.jsf.CurrencyConverter")
public class CurrencyConverter implements Converter<CurrencyUnit> {

	@Override
	public CurrencyUnit getAsObject(FacesContext context, UIComponent component,
			String value) {

		if (value == null) {
			return null;
		}
		return Monetary.getCurrency(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
							  CurrencyUnit value) {
		if (value == null) {
			return null;
		}
		return value.toString();
	}

}
