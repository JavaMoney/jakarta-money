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

import one.microstream.cdi.Store;
import one.microstream.cdi.StoreType;
import one.microstream.storage.types.StorageManager;

import java.util.Objects;

interface StoreStrategy {

    void store(Store store, StorageManager manager, StorageExtension extension);

    static StoreStrategy of(Store store) {
        Objects.requireNonNull(store, "store is required");
        if (StoreType.EAGER.equals(store.value())) {
            return StoreTypeStrategy.EAGER;
        }
        return StoreTypeStrategy.LAZY;
    }
}
