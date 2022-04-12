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

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converter to {@link CurrencyUnit} it serializes to {@link String} using the currency code.
 * @author Otavio Santana
 */
@Converter
public class CurrencyConverter implements AttributeConverter<CurrencyUnit, String>{

	@Override
	public String convertToDatabaseColumn(CurrencyUnit attribute) {
		if(attribute == null) {
			return null;
		}
		return attribute.toString();
	}

	@Override
	public CurrencyUnit convertToEntityAttribute(String dbData) {
		if(dbData == null){
			return null;
		}
		return Monetary.getCurrency(dbData);
	}

}
