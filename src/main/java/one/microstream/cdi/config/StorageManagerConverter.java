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

package one.microstream.cdi.config;

import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfigurationBuilder;
import one.microstream.storage.embedded.types.EmbeddedStorageFoundation;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import one.microstream.storage.types.StorageManager;
import org.eclipse.microprofile.config.spi.Converter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * A Config converter to {@link StorageManager}
 */
public class StorageManagerConverter implements Converter<StorageManager> {

    private static final Logger LOGGER = Logger.getLogger(StorageManagerConverter.class.getName());

    private static final Map<String, StorageManager> MAP = new ConcurrentHashMap<>();

    @Override
    public StorageManager convert(String value) throws IllegalArgumentException, NullPointerException {
        StorageManager storageManager = MAP.get(value);
        if (storageManager == null) {
            synchronized (MAP) {
                LOGGER.info("Loading configuration to start the class StorageManager from the key: " + value);
                EmbeddedStorageConfigurationBuilder configurationBuilder = EmbeddedStorageConfiguration.load(value);
                EmbeddedStorageFoundation<?> embeddedStorageFoundation = configurationBuilder.createEmbeddedStorageFoundation();
                EmbeddedStorageManager embeddedStorageManager = embeddedStorageFoundation.createEmbeddedStorageManager();
                EmbeddedStorageManager manager = embeddedStorageManager.start();
                MAP.put(value, manager);
                return manager;
            }
        }
        return storageManager;
    }
}