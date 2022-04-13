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
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.money.MonetaryOperator;

import org.javamoney.moneta.function.FastMoneyProducer;
import org.javamoney.moneta.function.MonetaryAmountProducer;
import org.javamoney.moneta.function.MoneyProducer;
import org.javamoney.moneta.function.RoundedMoneyProducer;

@ApplicationScoped
class MonetaryAmountProducerProducer {


	@Inject
	private MonetaryOperator rounding;

	@Produces
	@Default
	@MonetaryProducer
	public MonetaryAmountProducer getDefaultProducer(){
		return new MoneyProducer();
	}

	@Produces
	@MonetaryProducer(MonetaryAmountType.FAST_MONEY)
	public MonetaryAmountProducer getFastMoneyProducer(){
		return new FastMoneyProducer();
	}

	@Produces
	@MonetaryProducer(MonetaryAmountType.ROUNDED_MONEY)
	public MonetaryAmountProducer getRoundedMoneyProducer(){
		return new RoundedMoneyProducer(rounding);
	}

}
