/*
 *    Copyright 2021 Otavio Santana
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package one.microstream.cdi.extension;

import one.microstream.cdi.Store;
import one.microstream.cdi.StoreType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class StoreStrategyTest {

    @Test
    public void shouldReturnNPE() {
        Assertions.assertThrows(NullPointerException.class, () -> StoreStrategy.of(null));
    }

    @Test
    public void shouldReturnEager() {
        Store store = Mockito.mock(Store.class);
        Mockito.when(store.value()).thenReturn(StoreType.EAGER);
        StoreStrategy strategy = StoreStrategy.of(store);
        Assertions.assertEquals(StoreTypeStrategy.EAGER, strategy);
    }

    @Test
    public void shouldReturnLazy() {
        Store store = Mockito.mock(Store.class);
        Mockito.when(store.value()).thenReturn(StoreType.LAZY);
        StoreStrategy strategy = StoreStrategy.of(store);
        Assertions.assertEquals(StoreTypeStrategy.LAZY, strategy);
    }
}