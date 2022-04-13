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

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

import org.javamoney.moneta.convert.ExchangeRateType;

public class ExchangeRateProviderFactory {

	@Produces
	@Default
	@ExchangeRateQualifier
	public ExchangeRateProvider getDefault(){
		return MonetaryConversions.getExchangeRateProvider(ExchangeRateType.ECB);
	}

	@Produces
	@ExchangeRateQualifier(ExchangeRateType.ECB_HIST)
	public ExchangeRateProvider getECBHistory(){
		return MonetaryConversions.getExchangeRateProvider(ExchangeRateType.ECB_HIST);
	}

	@Produces
	@ExchangeRateQualifier(ExchangeRateType.ECB_HIST90)
	public ExchangeRateProvider getECBHistory90(){
		return MonetaryConversions.getExchangeRateProvider(ExchangeRateType.ECB_HIST90);
	}

	@Produces
	@ExchangeRateQualifier(ExchangeRateType.IMF)
	public ExchangeRateProvider getIMF(){
		return MonetaryConversions.getExchangeRateProvider(ExchangeRateType.IMF);
	}

	@Produces
	@ExchangeRateQualifier(ExchangeRateType.IMF_HIST)
	public ExchangeRateProvider getIMFHistory(){
		return MonetaryConversions.getExchangeRateProvider(ExchangeRateType.IMF_HIST);
	}
}
