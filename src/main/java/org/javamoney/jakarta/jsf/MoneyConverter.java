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
import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

/**
 * Converter to {@link MonetaryAmount} using the {@link Money} implementation using {@link Money#toString()}
 * and {@link Money#parse(CharSequence)}
 * @author Otavio Santana
 * @author Werner Keil
 */
@FacesConverter("money.midas.MoneyConverter")
public class MoneyConverter implements Converter<Money> {

	@Override
	public Money getAsObject(FacesContext context, UIComponent component,
			String value) {
		
		if (value == null) {
			return null;
		}
		return Money.parse(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
							  Money value) {
		if (value == null) {
			return null;
		}
		return value.toString();
	}

}
