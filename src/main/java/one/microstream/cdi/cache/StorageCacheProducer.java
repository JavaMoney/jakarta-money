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

import one.microstream.storage.types.StorageManager;
import org.eclipse.microprofile.config.Config;

import javax.annotation.PostConstruct;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.util.Objects;
import java.util.logging.Logger;


@ApplicationScoped
class StorageCacheProducer {

    private static final Logger LOGGER = Logger.getLogger(StorageCacheProducer.class.getName());

    private static final String CACHE_PROVIDER = "one.microstream.cache.types.CachingProvider";

    private CachingProvider provider;

    private CacheManager cacheManager;

    @Inject
    private Config config;

    @Inject
    private Instance<StorageManager> storageManager;

    @PostConstruct
    void setUp() {
        this.provider = Caching.getCachingProvider(CACHE_PROVIDER);
        this.cacheManager = provider.getCacheManager();
    }

    @Produces
    @StorageCache
    @ApplicationScoped
    CachingProvider getProvider() {
        return this.provider;
    }

    @Produces
    @StorageCache
    @ApplicationScoped
    CacheManager getManager() {
        return this.cacheManager;
    }

    @Produces
    @StorageCache
    public <K, V> Cache<K, V> producer(InjectionPoint injectionPoint) {
        StorageCacheProperty<K, V> cacheProperty = StorageCacheProperty.of(injectionPoint);
        String name = cacheProperty.getName();
        Class<K> key = cacheProperty.getKey();
        Class<V> value = cacheProperty.getValue();

        LOGGER.info("Loading cache: " + name + " the current caches: " + cacheManager.getCacheNames());

        Cache<K, V> cache;
        if (Objects.isNull(cacheManager.getCache(name, key, value))) {
            MutableConfigurationSupplier<K, V> supplier = MutableConfigurationSupplier
                    .of(cacheProperty, config, storageManager);
            LOGGER.info("Cache " + name + " does not exist. Creating with configuration: " + supplier);
            MutableConfiguration<K, V> configuration = supplier.get();
            cache = cacheManager.createCache(name, configuration);
        } else {
            cache = cacheManager.getCache(name);
        }
        return cache;
    }

    public void close(@Disposes @StorageCache CachingProvider provider) {
        provider.close();
    }

    public void close(@Disposes @StorageCache CacheManager manager) {
        manager.close();
    }
}
