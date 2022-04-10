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
package one.microstream.cdi;

import one.microstream.cdi.extension.BeanManagers;
import one.microstream.cdi.test.CDIExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.util.Set;

@CDIExtension
public class StorageTest {

    @Inject
    private Agenda agenda;

    @Inject
    private BeanManager beanManager;

    @Test
    @DisplayName("Should check if it create an instance by annotation")
    public void shouldCreateInstance() {
        Assertions.assertNotNull(this.agenda);
    }

    @Test
    public void shouldCreateNameRootInjection() {
        agenda.add("Otavio");
        agenda.add("Ada");
        Set<Bean<?>> beans = beanManager.getBeans(Agenda.class);
        Assertions.assertFalse(beans.isEmpty());
        Agenda instance = BeanManagers.getInstance(Agenda.class);
        Assertions.assertEquals(instance, agenda);
        Assertions.assertEquals(instance.getNames(), agenda.getNames());

    }
}
