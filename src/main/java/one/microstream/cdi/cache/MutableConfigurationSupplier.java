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

import javax.cache.configuration.Factory;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.ExpiryPolicy;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheWriter;
import javax.enterprise.inject.Instance;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * Create a Parser to explore the benefits of Eclipse MicroProfile Configuration
 *
 * @param <K> the key type in the cache
 * @param <V> the value type in the cache
 */
class MutableConfigurationSupplier<K, V> implements Supplier<MutableConfiguration<K, V>> {

    private static final Logger LOGGER = Logger.getLogger(MutableConfigurationSupplier.class.getName());

    private final StorageCacheProperty<K, V> cacheProperty;

    private final boolean storeByValue;

    private final boolean writeThrough;

    private final boolean readThrough;

    private final boolean managementEnabled;

    private final boolean statisticsEnabled;

    private final boolean storage;

    private final Factory<CacheLoader<K, V>> loaderFactory;

    private final Factory<CacheWriter<K, V>> writerFactory;

    private final Factory<ExpiryPolicy> expiryFactory;

    private final Instance<StorageManager> storageManager;

    private MutableConfigurationSupplier(StorageCacheProperty<K, V> cacheProperty, boolean storeByValue,
                                         boolean writeThrough, boolean readThrough,
                                         boolean managementEnabled, boolean statisticsEnabled,
                                         boolean storage,
                                         Factory<CacheLoader<K, V>> loaderFactory,
                                         Factory<CacheWriter<K, V>> writerFactory,
                                         Factory<ExpiryPolicy> expiryFactory,
                                         Instance<StorageManager> storageManager) {
        this.cacheProperty = cacheProperty;
        this.storeByValue = storeByValue;
        this.writeThrough = writeThrough;
        this.readThrough = readThrough;
        this.managementEnabled = managementEnabled;
        this.statisticsEnabled = statisticsEnabled;
        this.loaderFactory = loaderFactory;
        this.writerFactory = writerFactory;
        this.expiryFactory = expiryFactory;
        this.storage = storage;
        this.storageManager = storageManager;
    }

    public static <K, V> MutableConfigurationSupplier<K, V> of(StorageCacheProperty<K, V> cacheProperty,
                                                               Config config, Instance<StorageManager> storageManager) {
        boolean storeByValue = CacheProperties.isStoreByValue(config);
        boolean writeThrough = CacheProperties.isWriteThrough(config);
        boolean readThrough = CacheProperties.isReadThrough(config);
        boolean managementEnabled = CacheProperties.isManagementEnabled(config);
        boolean statisticsEnabled = CacheProperties.isStatisticsEnabled(config);
        boolean storage = CacheProperties.isStorage(config);
        Factory<CacheLoader<K, V>> loaderFactory = CacheProperties.getLoaderFactory(config);
        Factory<CacheWriter<K, V>> writerFactory = CacheProperties.getWriterFactory(config);
        Factory<ExpiryPolicy> expiryFactory = CacheProperties.getExpiryFactory(config);

        return new MutableConfigurationSupplier<>(cacheProperty, storeByValue, writeThrough,
                readThrough, managementEnabled, statisticsEnabled, storage,
                loaderFactory, writerFactory, expiryFactory, storageManager);
    }

    @Override
    public MutableConfiguration<K, V> get() {
        Class<K> key = cacheProperty.getKey();
        Class<V> value = cacheProperty.getValue();
        MutableConfiguration<K, V> configuration = new MutableConfiguration<>();
        configuration.setTypes(key, value);
        configuration.setStoreByValue(storeByValue)
                .setWriteThrough(writeThrough)
                .setReadThrough(readThrough)
                .setManagementEnabled(managementEnabled)
                .setStatisticsEnabled(statisticsEnabled);

        if (Objects.nonNull(loaderFactory)) {
            configuration.setCacheLoaderFactory(loaderFactory);
        }
        if (Objects.nonNull(writerFactory)) {
            configuration.setCacheWriterFactory(writerFactory);
        }
        if (Objects.nonNull(expiryFactory)) {
            configuration.setExpiryPolicyFactory(expiryFactory);
        }
        if (storage) {
            LOGGER.warning("The storage option is disable so, we'll ignore this option");
//            StorageManager storageManager = this.storageManager.get();
//            CacheStore<K, V> cacheStore = CacheStore.New(cacheProperty.getName(),storageManager);
//            configuration.setCacheLoaderFactory(() -> cacheStore);
//            configuration.setCacheWriterFactory(() -> cacheStore);
//            configuration.setWriteThrough(true);
//            configuration.setWriteThrough(true);
        }
        return configuration;
    }

    @Override
    public String toString() {
        return "MutableConfigurationSupplier{" +
                "cacheProperty=" + cacheProperty +
                ", storeByValue=" + storeByValue +
                ", writeThrough=" + writeThrough +
                ", readThrough=" + readThrough +
                ", managementEnabled=" + managementEnabled +
                ", statisticsEnabled=" + statisticsEnabled +
                ", storage=" + storage +
                ", loaderFactory=" + loaderFactory +
                ", writerFactory=" + writerFactory +
                ", expiryFactory=" + expiryFactory +
                '}';
    }
}
