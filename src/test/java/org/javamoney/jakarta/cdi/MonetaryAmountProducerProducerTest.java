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

import org.javamoney.jakarta.test.CDIExtension;
import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.RoundedMoney;
import org.javamoney.moneta.function.MonetaryAmountProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

@CDIExtension
class MonetaryAmountProducerProducerTest {

    @Inject
    private MonetaryAmountProducer amountProducer;

    @Inject
    @MonetaryProducer
    private MonetaryAmountProducer amountProducerA;

    @Inject
    @MonetaryProducer(MonetaryAmountType.FAST_MONEY)
    private MonetaryAmountProducer fastMoneyProducer;

    @Inject
    @MonetaryProducer(MonetaryAmountType.ROUNDED_MONEY)
    private MonetaryAmountProducer roundMoneyProducer;

    @Inject
    private CurrencyUnit currency;

    @Test
    public void shouldInjectDefaultAmountProducer() {
        Assertions.assertNotNull(amountProducer);
        MonetaryAmount amount = amountProducer.create(currency, 10);
        Assertions.assertNotNull(amount);
        Assertions.assertEquals(Money.class, amount.getClass());
    }

    @Test
    public void shouldInjectDefaultQualifierAmountProducer() {
        Assertions.assertNotNull(amountProducerA);
        MonetaryAmount amount = amountProducer.create(currency, 10);
        Assertions.assertNotNull(amount);
        Assertions.assertEquals(Money.class, amount.getClass());
    }

    @Test
    public void shouldInjectFastMoneyAmountProducer() {
        Assertions.assertNotNull(fastMoneyProducer);
        MonetaryAmount amount = fastMoneyProducer.create(currency, 10);
        Assertions.assertNotNull(amount);
        Assertions.assertEquals(FastMoney.class, amount.getClass());
    }

    @Test
    public void shouldInjectRoundedMoneyAmountProducer() {
        Assertions.assertNotNull(roundMoneyProducer);
        MonetaryAmount amount = roundMoneyProducer.create(currency, 10);
        Assertions.assertNotNull(amount);
        Assertions.assertEquals(RoundedMoney.class, amount.getClass());
    }
}