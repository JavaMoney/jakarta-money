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


/**
 * Define the {@link org.javamoney.moneta.function.MonetaryAmountProducer} that this library supports.
 * Each enum will return a different implementation
 */
public enum MonetaryAmountType {
    /**
     * the {@link  org.javamoney.moneta.function.MoneyProducer}
     */
    MONEY,
    /**
     * the {@link  org.javamoney.moneta.function.FastMoneyProducer}
     */
    FAST_MONEY,
    /**
     * the {@link  org.javamoney.moneta.function.RoundedMoneyProducer}
     */
    ROUNDED_MONEY;
}
