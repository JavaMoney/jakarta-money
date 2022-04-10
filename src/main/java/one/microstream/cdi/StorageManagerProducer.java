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

package one.microstream.cdi;

import one.microstream.reference.LazyReferenceManager;
import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfigurationBuilder;
import one.microstream.storage.types.StorageManager;
import org.eclipse.microprofile.config.Config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.util.Map;
import java.util.logging.Logger;

@ApplicationScoped
class StorageManagerProducer {

    private static final Logger LOGGER = Logger.getLogger(StorageManagerProducer.class.getName());

    @Inject
    private Config config;

    @Produces
    @ApplicationScoped
    public StorageManager getStoreManager() {

        Map<String, String> properties = ConfigurationCoreProperties.getProperties(config);
        LOGGER.info("Loading default StorageManager loading from MicroProfile Config properties. The keys: "
                + properties.keySet());

        EmbeddedStorageConfigurationBuilder builder = EmbeddedStorageConfigurationBuilder.New();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            builder.set(entry.getKey(), entry.getValue());
        }
        return builder.createEmbeddedStorageFoundation().start();
    }

    public void dispose(@Disposes StorageManager manager) {
        LOGGER.info("Closing the default StorageManager");
        manager.close();
        LOGGER.info("Closing the LazyReferenceManager");
        LazyReferenceManager referenceManager = LazyReferenceManager.get();
        referenceManager.stop();
    }
}
