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

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;


@Provider
public class CurrencyUnitConverterProvider implements ParamConverterProvider {

    private static final ParamConverter<CurrencyUnit> INSTANCE = new CurrencyParamConverter();

    @Override
    public <T> ParamConverter<T> getConverter(final Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.equals(CurrencyUnit.class)) {
            return (ParamConverter<T>) INSTANCE;
        }
        return null;
    }

    private static class CurrencyParamConverter implements ParamConverter<CurrencyUnit> {

        @Override
        public CurrencyUnit fromString(String value) {
            if (value == null || value.isEmpty()) {
                return null;
            }
            return Monetary.getCurrency(value);
        }

        @Override
        public String toString(CurrencyUnit value) {
            if (value == null) {
                return null;
            }
            return value.toString();
        }
    }
}
