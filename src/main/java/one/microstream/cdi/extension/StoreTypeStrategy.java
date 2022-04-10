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

import one.microstream.cdi.MicrostreamException;
import one.microstream.cdi.Store;
import one.microstream.persistence.types.PersistenceStoring;
import one.microstream.persistence.types.Storer;
import one.microstream.storage.types.StorageManager;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

enum StoreTypeStrategy implements StoreStrategy {

    EAGER {
        @Override
        public void store(Store store, StorageManager manager, StorageExtension extension) {
            LOGGER.log(Level.WARNING, "Store with Eager has a high cost of performance.");
            Object root = manager.root();
            Storer storer = manager.createEagerStorer();
            execute(store, extension, root, storer);
            storer.commit();

        }

    }, LAZY {
        @Override
        public void store(Store store, StorageManager manager, StorageExtension extension) {
            Object root = manager.root();
            execute(store, extension, root, manager);
        }
    };

    private static final Logger LOGGER = Logger.getLogger(StoreTypeStrategy.class.getName());

    private static void execute(Store store, StorageExtension extension, Object root, PersistenceStoring storing) {
        if (store.root()) {
            storeRoot(root, storing);
        } else {
            Optional<EntityMetadata> metadata = extension.get(root.getClass());
            if (metadata.isEmpty()) {
                LOGGER.log(Level.FINEST, "There is no entity with the @Storage annotation to the current root class " +
                        root.getClass() + " so it will store by root");
                storeRoot(root, storing);
            }
            metadata.ifPresent(m -> m.values(root, store.fields()).forEach(storing::store));
            LOGGER.log(Level.FINEST, "Storing Iterables and Maps fields from the root class "
                    + root.getClass() + " the fields: " + store.fields());
        }
    }

    private static void storeRoot(Object root, PersistenceStoring storing) {
        long storeId = storing.store(root);
        LOGGER.log(Level.WARNING, "Store the root it might return performance issue " + storeId);
    }


}
