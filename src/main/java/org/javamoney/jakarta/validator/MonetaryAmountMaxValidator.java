/*
 * Copyright (c) 2002, Otavio Santana and others by the @author tag.
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

import java.math.BigDecimal;
import javax.money.MonetaryAmount;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MonetaryAmountMaxValidator implements ConstraintValidator<MonetaryMax, MonetaryAmount>{

	private BigDecimal number;

	@Override
	public void initialize(MonetaryMax constraintAnnotation) {
		number = new BigDecimal(constraintAnnotation.value());
	}

	@Override
	public boolean isValid(MonetaryAmount value,
			ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		return value.isLessThanOrEqualTo(value.getFactory().setNumber(number).create());
	}
}
