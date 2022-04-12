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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

class CurrencyReaderConverter {

	private final List<CurrencyUnit> currencies = new ArrayList<>();

	public CurrencyReaderConverter(CurrencyAccepted currency) {
		currencies.addAll(createCurrencyList(currency.currencies()));
		currencies.addAll(createCurrencyListFromLocale(currency.currenciesFromLocales()));

	}

	public CurrencyReaderConverter(CurrencyRejected currency) {
		currencies.addAll(createCurrencyList(currency.currencies()));
		currencies.addAll(createCurrencyListFromLocale(currency.currenciesFromLocales()));
	}


	private List<CurrencyUnit> createCurrencyList(String[] currenciesTexts) {
		if (isEmptyArray(currenciesTexts)) {
			return Collections.emptyList();
		}
		List<CurrencyUnit> currenciesFromCode = new ArrayList<>();
		for (String value : currenciesTexts) {
			currenciesFromCode.add(Monetary.getCurrency(value.trim()));
		}
		return currenciesFromCode;
	}


	private boolean isEmptyArray(String[] currenciesTexts) {
		return currenciesTexts.length == 0 || currenciesTexts.length == 1 && currenciesTexts[0].isEmpty();
	}

	private List<CurrencyUnit> createCurrencyListFromLocale(
			String[] currenciesTexts) {

		if (isEmptyArray(currenciesTexts)) {
			return Collections.emptyList();
		}
		List<CurrencyUnit> currenciesFromLocale = new ArrayList<>();
		for (String value : currenciesTexts) {
			String[] aux = value.split("_");
			if (aux.length == 2) {
				currenciesFromLocale
						.add(Monetary.getCurrency(new Locale(aux[0], aux[1])));
			} else {
				throw new IllegalArgumentException(
						"On error happened on parameter: " + value);
			}
		}
		return currenciesFromLocale;
	}

	List<CurrencyUnit> getCurrencies() {
		return currencies;
	}

}
