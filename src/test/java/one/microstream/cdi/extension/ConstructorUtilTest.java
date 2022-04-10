/*
 *    Copyright 2022 Otavio Santana
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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConstructorUtilTest {

    @Test
    @DisplayName("Should return NPE when it uses")
    public void shouldReturnNPEWhenThereIsNull() {
        Assertions.assertThrows(NullPointerException.class, () -> ConstructorUtil.create(null));
    }

    @Test
    public void shouldReturnErrorWhenThereIsInterface() {
        Assertions.assertThrows(ConstructorException.class, () -> ConstructorUtil.create(Animal.class));
    }

    @Test
    public void shouldReturnErrorWhenThereNoDefaultConstructor() {
        Assertions.assertThrows(ConstructorException.class, () -> ConstructorUtil.create(Lion.class));
    }

    @Test
    public void shouldReturnConstructor() {
        Tiger tiger = ConstructorUtil.create(Tiger.class);
        Assertions.assertNotNull(tiger);
    }

    @Test
    public void shouldCreateDefaultConstructor() {
        Cat cat = ConstructorUtil.create(Cat.class);
        Assertions.assertNotNull(cat);
    }
}