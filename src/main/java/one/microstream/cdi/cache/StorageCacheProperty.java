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

import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Load info the belongs to the {@link StorageCache} annotation
 *
 * @param <K> the key property
 * @param <V> the value property
 */
class StorageCacheProperty<K, V> {

    private final Class<K> key;

    private final Class<V> value;

    private final String name;

    private StorageCacheProperty(Class<K> key, Class<V> value, String name) {
        this.key = key;
        this.value = value;
        this.name = name;
    }

    public Class<K> getKey() {
        return key;
    }

    public Class<V> getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StorageCacheProperty<?, ?> that = (StorageCacheProperty<?, ?>) o;
        return Objects.equals(key, that.key) && Objects.equals(value, that.value) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, name);
    }

    @Override
    public String toString() {
        return "StorageCacheProperty{" +
                "key=" + key +
                ", value=" + value +
                ", name='" + name + '\'' +
                '}';
    }

    public static <K, V> StorageCacheProperty<K, V> of(InjectionPoint injectionPoint) {
        Annotated annotated = injectionPoint.getAnnotated();
        StorageCache storageCache = annotated.getAnnotation(StorageCache.class);
        String cacheName = storageCache.value();
        Member member = injectionPoint.getMember();
        if (member instanceof Field) {
            Field field = (Field) member;
            ParameterizedType genericType = (ParameterizedType) field.getGenericType();
            Type[] arguments = genericType.getActualTypeArguments();
            Class<K> key = (Class<K>) arguments[0];
            Class<V> value = (Class<V>) arguments[1];
            return new StorageCacheProperty<>(key, value, cacheName);
        }
        throw new IllegalArgumentException("");
    }
}
