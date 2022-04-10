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

class StorageException extends MicrostreamException {

    private static final String MESSAGE = "There is an incompatibility between the entity and the" +
            " current root in the StorageManager. Please check the compatibility. " +
            "Entity: %s and current root class %s";

    <T, E> StorageException(Class<T> entity, Class<E> root) {
        super(String.format(MESSAGE, entity, root));
    }
}
