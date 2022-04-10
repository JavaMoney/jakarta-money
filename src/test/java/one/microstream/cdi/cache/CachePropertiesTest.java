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
import org.eclipse.microprofile.config.Config;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.cache.configuration.Factory;
import javax.cache.expiry.ExpiryPolicy;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheWriter;
import javax.inject.Inject;

@CDIExtension
class CachePropertiesTest {

    @Inject
    private Config config;

    @Test
    @DisplayName("Should get the default value in the storeByValue")
    public void shouldReturnStoreByValue() {
        boolean storeByValue = CacheProperties.isStoreByValue(config);
        Assertions.assertFalse(storeByValue);
    }

    @Test
    public void shouldReturnErrorWhenConfigIsNull() {
        Assertions.assertThrows(NullPointerException.class, () -> CacheProperties.isStoreByValue(null));
        Assertions.assertThrows(NullPointerException.class, () -> CacheProperties.isWriteThrough(null));
        Assertions.assertThrows(NullPointerException.class, () -> CacheProperties.isReadThrough(null));
        Assertions.assertThrows(NullPointerException.class, () -> CacheProperties.isManagementEnabled(null));
        Assertions.assertThrows(NullPointerException.class, () -> CacheProperties.isStatisticsEnabled(null));
        Assertions.assertThrows(NullPointerException.class, () -> CacheProperties.getLoaderFactory(null));
        Assertions.assertThrows(NullPointerException.class, () -> CacheProperties.getWriterFactory(null));
        Assertions.assertThrows(NullPointerException.class, () -> CacheProperties.getExpiryFactory(null));
        Assertions.assertThrows(NullPointerException.class, () -> CacheProperties.isStorage(null));
    }

    @Test
    @DisplayName("Should get storeByValue from Eclipse MicroProfile Config")
    public void shouldReturnStoreByValueConfig() {
        System.setProperty(CacheProperties.CACHE_STORE_VALUE.get(), "false");
        Assertions.assertFalse(CacheProperties.isStoreByValue(config));
        System.setProperty(CacheProperties.CACHE_STORE_VALUE.get(), "true");
        Assertions.assertTrue(CacheProperties.isStoreByValue(config));
        System.clearProperty(CacheProperties.CACHE_STORE_VALUE.get());
    }

    @Test
    @DisplayName("Should get the default value in the writeThrough")
    public void shouldReturnWriteThrough() {
        boolean storeByValue = CacheProperties.isWriteThrough(config);
        Assertions.assertFalse(storeByValue);
    }

    @Test
    @DisplayName("Should get writeThrough from Eclipse MicroProfile Config")
    public void shouldReturnSWriteThroughConfig() {
        System.setProperty(CacheProperties.CACHE_WRITE_THROUGH.get(), "false");
        Assertions.assertFalse(CacheProperties.isWriteThrough(config));
        System.setProperty(CacheProperties.CACHE_WRITE_THROUGH.get(), "true");
        Assertions.assertTrue(CacheProperties.isWriteThrough(config));
        System.clearProperty(CacheProperties.CACHE_WRITE_THROUGH.get());
    }

    @Test
    @DisplayName("Should get the default value in the readThrough")
    public void shouldReturnReadThrough() {
        boolean storeByValue = CacheProperties.isReadThrough(config);
        Assertions.assertFalse(storeByValue);
    }

    @Test
    @DisplayName("Should get readThrough from Eclipse MicroProfile Config")
    public void shouldReturnReadThroughConfig() {
        System.setProperty(CacheProperties.CACHE_READ_THROUGH.get(), "false");
        Assertions.assertFalse(CacheProperties.isReadThrough(config));
        System.setProperty(CacheProperties.CACHE_READ_THROUGH.get(), "true");
        Assertions.assertTrue(CacheProperties.isReadThrough(config));
        System.clearProperty(CacheProperties.CACHE_READ_THROUGH.get());
    }

    @Test
    @DisplayName("Should get the default value in the managementEnabled")
    public void shouldReturnManagementEnabled() {
        boolean storeByValue = CacheProperties.isManagementEnabled(config);
        Assertions.assertFalse(storeByValue);
    }

    @Test
    @DisplayName("Should get managementEnabled from Eclipse MicroProfile Config")
    public void shouldReturnManagementEnabledConfig() {
        System.setProperty(CacheProperties.CACHE_MANAGEMENT.get(), "false");
        Assertions.assertFalse(CacheProperties.isManagementEnabled(config));
        System.setProperty(CacheProperties.CACHE_MANAGEMENT.get(), "true");
        Assertions.assertTrue(CacheProperties.isManagementEnabled(config));
        System.clearProperty(CacheProperties.CACHE_MANAGEMENT.get());
    }

    @Test
    @DisplayName("Should get the default value in the statisticsEnabled")
    public void shouldReturnStatisticsEnabled() {
        boolean storeByValue = CacheProperties.isStatisticsEnabled(config);
        Assertions.assertFalse(storeByValue);
    }

    @Test
    @DisplayName("Should get statisticsEnabled from Eclipse MicroProfile Config")
    public void shouldReturnStatisticsEnabledConfig() {
        System.setProperty(CacheProperties.CACHE_STATISTICS.get(), "false");
        Assertions.assertFalse(CacheProperties.isStatisticsEnabled(config));
        System.setProperty(CacheProperties.CACHE_STATISTICS.get(), "true");
        Assertions.assertTrue(CacheProperties.isStatisticsEnabled(config));
        System.clearProperty(CacheProperties.CACHE_STATISTICS.get());
    }

    @Test
    public void shouldReturnNullAsDefaultLoaderFactory() {
        Assertions.assertNull(CacheProperties.getLoaderFactory(config));
    }

    @Test
    public void shouldReturnNullAsDefaultWriterFactory() {
        Assertions.assertNull(CacheProperties.getWriterFactory(config));
    }

    @Test
    public void shouldReturnNullAsDefaultExpireFactory() {
        Assertions.assertNull(CacheProperties.getExpiryFactory(config));
    }

    @Test
    public void shouldReturnErrorWhenIsNotFactoryInstance() {
        System.setProperty(CacheProperties.CACHE_LOADER_FACTORY.get(), "java.lang.String");
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> Assertions.assertNull(CacheProperties.getLoaderFactory(config)));
        System.clearProperty(CacheProperties.CACHE_LOADER_FACTORY.get());
    }

    @Test
    public void shouldCreateLoaderFactory() {
        System.setProperty(CacheProperties.CACHE_LOADER_FACTORY.get(), MockLoaderFactory.class.getName());
        Factory<CacheLoader<Object, Object>> loaderFactory = CacheProperties.getLoaderFactory(config);
        Assertions.assertTrue(loaderFactory instanceof MockLoaderFactory);
        System.clearProperty(CacheProperties.CACHE_LOADER_FACTORY.get());
    }

    @Test
    public void shouldCreateCacheWriterFactory() {
        System.setProperty(CacheProperties.CACHE_WRITER_FACTORY.get(), MockCacheWriter.class.getName());
        Factory<CacheWriter<Object, Object>> loaderFactory = CacheProperties.getWriterFactory(config);
        Assertions.assertTrue(loaderFactory instanceof MockCacheWriter);
        System.clearProperty(CacheProperties.CACHE_WRITER_FACTORY.get());
    }

    @Test
    public void shouldCreateExpireFactory() {
        System.setProperty(CacheProperties.CACHE_EXPIRES_FACTORY.get(), MockExpiryPolicy.class.getName());
        Factory<ExpiryPolicy> loaderFactory = CacheProperties.getExpiryFactory(config);
        Assertions.assertTrue(loaderFactory instanceof MockExpiryPolicy);
        System.clearProperty(CacheProperties.CACHE_EXPIRES_FACTORY.get());
    }

    @Test
    @DisplayName("Should get storage from Eclipse MicroProfile Config")
    public void shouldReturnStorage() {
        System.setProperty(CacheProperties.STORAGE.get(), "false");
        Assertions.assertFalse(CacheProperties.isStorage(config));
        System.setProperty(CacheProperties.STORAGE.get(), "true");
        Assertions.assertTrue(CacheProperties.isStorage(config));
        System.clearProperty(CacheProperties.STORAGE.get());
    }


}