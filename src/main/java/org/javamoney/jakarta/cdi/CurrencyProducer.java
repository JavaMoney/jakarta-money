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

package org.javamoney.jakarta.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@ApplicationScoped
class CurrencyProducer {

    @Inject
    private Instance<HttpServletRequest> request;

    @Produces
    @Default
    public CurrencyUnit getDefault() {
        Locale locale = getLocale();
        return Monetary.getCurrency(locale);
    }

    private Locale getLocale() {
        if (request.isResolvable()) {
            HttpServletRequest servletRequest = request.get();
            return servletRequest.getLocale();
        }
        return Locale.getDefault();
    }


}
