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

/**
 * Defines the way the instance that will be stored in the  {@link one.microstream.storage.types.StorageManager}.
 * <p>
 * E.g.: Given we have an Inventory class with a name and a list of products.
 * Lazy: will {@link one.microstream.storage.types.StorageManager#store} the list of products:
 * storageManager.store(inventory.getProducts());
 * <p>
 * EAGER: will {@link one.microstream.storage.types.StorageManager#store} the root instance:
 * storageManager.store(inventory);
 * To get more information: https://docs.microstream.one/manual/storage/storing-data/lazy-eager-full.html
 */
public enum StoreType {
    /**
     * Lazy storing is the default storing mode of the MicroStream engine.
     * Referenced instances are stored only if they have not been stored yet.
     * If a referenced instance has been stored previously it is not stored again even if it has been modified.
     * It will use the method {@link one.microstream.storage.types.StorageManager#store}
     * E.g.: The list of products: storageManager.store(inventory.getProducts());
     */
    LAZY,
    /**
     * In eager storing mode referenced instances are stored even if they had been stored before.
     * Contrary to Lazy storing this will also store modified child objects at the cost of performance.
     * It will {@link one.microstream.storage.types.StorageManager#createEagerStorer}.
     * E.g.:
     * Storer storer = storage.createEagerStorer();
     * storer.store(inventory.getProducts());
     * storer.commit();
     */
    EAGER;
}
