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

import static org.junit.jupiter.api.Assertions.*;

import one.microstream.cdi.test.CDIExtension;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

@CDIExtension
class BeanManagersTest {

    @Inject
    private BeanManager beanManager;

    @Test
    public void shouldInstance() {
        final StorageExtension instance = BeanManagers.getInstance(StorageExtension.class, beanManager);
        assertNotNull(instance);
    }

    @Test
    public void shouldInstanceWithQualifier() {
        final StorageExtension instance = BeanManagers.getInstance(StorageExtension.class
                , new Default.Literal(), beanManager);
        assertNotNull(instance);
    }

    @Test
    public void shouldReturnBeanManager() {
        final BeanManager beanManager = BeanManagers.getBeanManager();
        assertNotNull(beanManager);
    }

    @Test
    public void shouldInstanceWithCurrentBeanManager() {
        final StorageExtension instance = BeanManagers.getInstance(StorageExtension.class);
        assertNotNull(instance);
    }

    @Test
    public void shouldInstanceWithQualifierWithCurrentBeanManager() {
        final StorageExtension instance = BeanManagers.getInstance(StorageExtension.class
                , new Default.Literal());
        assertNotNull(instance);
    }

}
