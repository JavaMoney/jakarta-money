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

package one.microstream.cdi.cache;

import one.microstream.cdi.test.CDIExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.cache.Cache;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.util.Set;

@CDIExtension
public class StorageCacheProducerTest {

    @Inject
    @StorageCache
    private Cache<Integer, String> cache;

    @Inject
    @StorageCache("jcache2")
    private Cache<Integer, String> cacheB;

    @Inject
    private BeanManager beanManager;

    @Test
    public void shouldCreateInjectCache() {
        cache.put(1, "one");
        Assertions.assertEquals("one", cache.get(1));
    }

    @Test
    public void shouldNotCreateNonQualifierCache() {
        Set<Bean<?>> beans = beanManager.getBeans(Cache.class);
        Assertions.assertEquals(0, beans.size());
    }

    @Test
    public void shouldShouldHaveDifferentInstance() {
        cache.put(1, "one");
        Assertions.assertNotEquals(cache, cacheB);
        Assertions.assertTrue(cache.containsKey(1));
        Assertions.assertFalse(cacheB.containsKey(1));
    }


}
