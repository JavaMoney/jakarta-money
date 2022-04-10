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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.PassivationCapable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;


/**
 * A template class to all the {@link Bean} at the Eclipse JNoSQL artemis project.
 * It will work as Template method.
 *
 * @param <T> the bean type
 */
public abstract class AbstractBean<T> implements Bean<T>, PassivationCapable {


    private final BeanManager beanManager;

    protected AbstractBean(BeanManager beanManager) {
        this.beanManager = beanManager;
    }


    protected <T> T getInstance(Class<T> clazz) {
        return BeanManagers.getInstance(clazz, beanManager);
    }

    protected <T> T getInstance(Class<T> clazz, Annotation qualifier) {
        return BeanManagers.getInstance(clazz, qualifier, beanManager);
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints() {
        return Collections.emptySet();
    }

    @Override
    public boolean isNullable() {
        return false;
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return ApplicationScoped.class;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Set<Class<? extends Annotation>> getStereotypes() {
        return Collections.emptySet();
    }

    @Override
    public boolean isAlternative() {
        return false;
    }

    @Override
    public void destroy(T instance, CreationalContext<T> context) {

    }

}