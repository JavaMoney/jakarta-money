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

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.InjectionException;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Set;

/**
 * Utilitarian class to {@link javax.enterprise.inject.spi.BeanManager}
 */
public class BeanManagers {

    /**
     * Get instance from the {@link BeanManager}
     *
     * @param clazz       the clazz to inject from the BeanManager
     * @param beanManager the BeanManager
     * @param <T>         the instance type
     * @return the instance from CDI context
     */
    public static <T> T getInstance(Class<T> clazz, BeanManager beanManager) {
        Objects.requireNonNull(clazz, "clazz is required");
        Objects.requireNonNull(beanManager, "beanManager is required");
        return getInstanceImpl(clazz, beanManager);
    }

    /**
     * Get instance from the {@link BeanManager}
     *
     * @param clazz       the clazz to inject from the BeanManager
     * @param beanManager the BeanManager
     * @param qualifier   the qualifier
     * @param <T>         the instance type
     * @return the instance from CDI context
     */
    public static <T> T getInstance(Class<T> clazz, Annotation qualifier, BeanManager beanManager) {
        Objects.requireNonNull(clazz, "clazz is required");
        Objects.requireNonNull(qualifier, "qualifier is required");
        Objects.requireNonNull(beanManager, "beanManager is required");

        return getInstanceImpl(clazz, qualifier, beanManager);
    }

    /**
     * Get the CDI BeanManager for the current CDI context
     *
     * @return the BeanManager
     */
    public static BeanManager getBeanManager() {
        return CDI.current().getBeanManager();
    }

    /**
     * Get instance from the {@link BeanManager} using the {@link BeanManagers#getBeanManager()}
     *
     * @param clazz the clazz to inject from the BeanManager
     * @param <T>   the instance type
     * @return the instance from CDI context
     */
    public static <T> T getInstance(Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz is required");
        return getInstanceImpl(clazz, getBeanManager());
    }

    /**
     * Get instance from the {@link BeanManager} using the {@link BeanManagers#getBeanManager()}
     *
     * @param clazz     the clazz to inject from the BeanManager
     * @param qualifier the qualifier
     * @param <T>       the instance type
     * @return the instance from CDI context
     */
    public static <T> T getInstance(Class<T> clazz, Annotation qualifier) {
        Objects.requireNonNull(clazz, "clazz is required");
        return getInstanceImpl(clazz, qualifier, getBeanManager());
    }

    private static <T> T getInstanceImpl(Class<T> clazz, BeanManager beanManager) {
        Set<Bean<?>> beans = beanManager.getBeans(clazz);
        if (beans.isEmpty()) {
            throw new InjectionException("Does not find the bean class: " + clazz + " into CDI container");
        }
        Bean<T> bean = (Bean<T>) beans.iterator().next();
        CreationalContext<T> ctx = beanManager.createCreationalContext(bean);
        return (T) beanManager.getReference(bean, clazz, ctx);
    }

    private static <T> T getInstanceImpl(Class<T> clazz, Annotation qualifier, BeanManager beanManager) {
        Set<Bean<?>> beans = beanManager.getBeans(clazz, qualifier);
        checkInjection(clazz, beans);
        Bean<T> bean = (Bean<T>) beans.iterator().next();
        CreationalContext<T> ctx = beanManager.createCreationalContext(bean);
        return (T) beanManager.getReference(bean, clazz, ctx);
    }

    private static <T> void checkInjection(Class<T> clazz, Set<Bean<?>> beans) {
        if (beans.isEmpty()) {
            throw new InjectionException("Does not find the bean class: " + clazz + " into CDI container");
        }
    }

    private BeanManagers() {
    }
}
