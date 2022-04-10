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

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Supplier;

class FieldMetadata implements Supplier<Field> {

    private final Field field;

    private final String name;

    private FieldMetadata(Field field) {
        this.field = field;
        this.name = field.getName();
    }

    Object read(Object entity) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new MicrostreamException("There is an issue to read the field: " + field, e);
        }
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Field get() {
        return field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FieldMetadata that = (FieldMetadata) o;
        return Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(field);
    }

    @Override
    public String toString() {
        return "FieldMetadata{" +
                "field=" + field +
                ", name='" + name + '\'' +
                '}';
    }

    static FieldMetadata of(Field field) {
        return new FieldMetadata(field);
    }

}
