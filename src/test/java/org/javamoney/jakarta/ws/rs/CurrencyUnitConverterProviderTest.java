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

package org.javamoney.jakarta.ws.rs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyUnitConverterProviderTest {

    private ParamConverterProvider converterProvider;

    @BeforeEach
    public void setUp() {
        this.converterProvider = new CurrencyUnitConverterProvider();
    }

    @Test
    public void shouldReturnParamConverterWhenIsCurrencyUnit() {
        ParamConverter<CurrencyUnit> converter = getConverter();
        Assertions.assertNotNull(converter);
    }

    @Test
    public void shouldReturnNullWhenIsNotCurrency() {
        ParamConverter<String> converter = this.converterProvider.getConverter(String.class, null, null);
        Assertions.assertNull(converter);
    }

    @Test
    public void shouldCreateFromString() {
        ParamConverter<CurrencyUnit> converter = getConverter();
        CurrencyUnit currency = converter.fromString("USD");
        Assertions.assertNotNull(currency);
        Assertions.assertEquals("USD", currency.getCurrencyCode());
    }

    @Test
    public void shouldToString() {
        ParamConverter<CurrencyUnit> converter = getConverter();
        CurrencyUnit currency = Monetary.getCurrency("USD");
        String dollar = converter.toString(currency);
        Assertions.assertEquals("USD", dollar);
    }

    private ParamConverter<CurrencyUnit> getConverter() {
        ParamConverter<CurrencyUnit> converter = this.converterProvider.getConverter(CurrencyUnit.class, null, null);
        return converter;
    }

}