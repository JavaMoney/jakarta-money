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
import one.microstream.storage.types.StorageManager;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.BeanManager;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Storage Discovery Bean to CDI extension to register an entity with {@link one.microstream.cdi.Storage} annotation
 */
class StorageBean<T> extends AbstractBean<T> {

    private final Class<T> type;

    private final Set<Type> types;

    private final Set<Annotation> qualifiers;

    protected StorageBean(BeanManager beanManager, Class<T> type) {
        super(beanManager);
        this.type = type;
        this.types = Collections.singleton(type);
        this.qualifiers = new HashSet<>();
        qualifiers.add(AnnotationLiteralUtil.DEFAULT_ANNOTATION);
        qualifiers.add(AnnotationLiteralUtil.ANY_ANNOTATION);
    }

    @Override
    public Class<T> getBeanClass() {
        return type;
    }


    @Override
    public T create(CreationalContext<T> context) {
        StorageManager manager = getInstance(StorageManager.class);
        Object root = manager.root();
        T entity;
        if (Objects.isNull(root)) {
            entity = ConstructorUtil.create(type);
            manager.setRoot(entity);
            manager.storeRoot();
        } else {
            if (type.isInstance(root)) {
                entity = (T) root;
            } else {
                throw new StorageException(type, root.getClass());
            }
        }
        return entity;
    }

    @Override
    public Set<Class<? extends Annotation>> getStereotypes() {
        return Collections.singleton(Storage.class);
    }

    @Override
    public Set<Type> getTypes() {
        return types;
    }

    @Override
    public Set<Annotation> getQualifiers() {
        return qualifiers;
    }

    @Override
    public String getId() {
        return type.getName() + " @Storage";
    }

    @Override
    public String toString() {
        return "StorageBean{" +
                "type=" + type +
                ", types=" + types +
                ", qualifiers=" + qualifiers +
                '}';
    }
}
