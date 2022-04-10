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

import one.microstream.cdi.Storage;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.WithAnnotations;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

/**
 * This extension will look for entities with the Storage and then load it.
 */
@ApplicationScoped
public class StorageExtension implements Extension {

    private static final Logger LOGGER = Logger.getLogger(StorageExtension.class.getName());

    private final Set<Class<?>> storageRoot = new HashSet<>();

    private final Map<Class<?>, EntityMetadata> entities = new HashMap<>();

    <T> void loadEntity(@Observes @WithAnnotations({Storage.class}) final ProcessAnnotatedType<T> target) {
        AnnotatedType<T> annotatedType = target.getAnnotatedType();
        if (annotatedType.isAnnotationPresent(Storage.class)) {
            Class<T> javaClass = target.getAnnotatedType().getJavaClass();
            storageRoot.add(javaClass);
            LOGGER.info("New class found annotated with @Storage is " + javaClass);
        }
    }

    void onAfterBeanDiscovery(@Observes final AfterBeanDiscovery afterBeanDiscovery, final BeanManager beanManager) {
        LOGGER.info(String.format("Processing StorageExtension:  %d found", storageRoot.size()));
        if (storageRoot.size() > 1) {
            throw new IllegalStateException("In the application must have only a class with the Storage annotation, classes: "
                    + storageRoot);
        }
        storageRoot.forEach(entity -> {
            StorageBean<?> bean = new StorageBean<>(beanManager, entity);
            afterBeanDiscovery.addBean(bean);
            entities.put(entity, EntityMetadata.of(entity));
        });
    }

    <T> Optional<EntityMetadata> get(Class<T> entity) {
        return Optional.ofNullable(this.entities.get(entity));
    }

    @Override
    public String toString() {
        return "StorageExtension{" +
                "storageRoot=" + storageRoot +
                ", entities=" + entities +
                '}';
    }
}
