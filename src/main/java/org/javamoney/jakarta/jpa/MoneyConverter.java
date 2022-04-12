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

import javax.money.MonetaryAmount;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.javamoney.moneta.Money;

/**
 * Converter to {@link MonetaryAmount} using the {@link Money} implementation using {@link Money#toString()}
 * and {@link Money#parse(CharSequence)}
 * @author Otavio Santana
 * @author Werner Keil
 */
@Converter
public class MoneyConverter implements AttributeConverter<MonetaryAmount, String> {

	@Override
	public String convertToDatabaseColumn(MonetaryAmount attribute) {
		
		if (attribute == null) {
			return null;
		}
		return Money.from(attribute).toString();
	}

	@Override
	public MonetaryAmount convertToEntityAttribute(String dbData){
		if (dbData == null) {
			return null;
		}
		return Money.parse(dbData);
	}

}