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
import org.javamoney.moneta.function.FastMoneyProducer;
import org.javamoney.moneta.function.MonetaryAmountProducer;
import org.javamoney.moneta.function.MoneyProducer;
import org.javamoney.moneta.function.RoundedMoneyProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@CDIExtension
class ProducerFactoryTest {

    @Inject
    private MonetaryAmountProducer amountProducer;

    @Inject
    @MonetaryAmountQualifier
    private MonetaryAmountProducer amountProducerA;

    @Inject
    @MonetaryAmountQualifier(MonetaryAmountType.FAST_MONEY)
    private MonetaryAmountProducer fastMoneyProducer;

    @Inject
    @MonetaryAmountQualifier(MonetaryAmountType.ROUNDED_MONEY)
    private MonetaryAmountProducer roundMoneyProducer;

    @Test
    public void shouldInjectDefaultAmountProducer() {
        Assertions.assertNotNull(amountProducer);
        Assertions.assertEquals(MoneyProducer.class, amountProducer.getClass());
    }

    @Test
    public void shouldInjectDefaultQualifierAmountProducer() {
        Assertions.assertNotNull(amountProducerA);
        Assertions.assertEquals(MoneyProducer.class, amountProducerA.getClass());
    }

    @Test
    public void shouldInjectFastMoneyAmountProducer() {
        Assertions.assertNotNull(fastMoneyProducer);
        Assertions.assertEquals(FastMoneyProducer.class, fastMoneyProducer.getClass());
    }

    @Test
    public void shouldInjectRoundedMoneyAmountProducer() {
        Assertions.assertNotNull(roundMoneyProducer);
        Assertions.assertEquals(RoundedMoneyProducer.class, roundMoneyProducer.getClass());
    }
}