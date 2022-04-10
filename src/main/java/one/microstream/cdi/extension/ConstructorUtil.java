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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public final class ConstructorUtil {
    private ConstructorUtil() {
    }


   public static <T> T create(Class<T> entity) {
        List<Constructor<?>> constructors = Stream.
                of(entity.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == 0)
                .collect(toList());

        if (constructors.isEmpty()) {
            throw new ConstructorException(entity);
        }
        Constructor<?> constructor = constructors.get(0);
        try {
            return (T) constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            throw new MicrostreamException("There is an issue to create the Storage class: " + entity.getName());
        }
    }
}
