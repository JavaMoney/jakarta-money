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
package one.microstream.cdi.test;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.AnnotationUtils;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import java.util.function.Consumer;
import java.util.function.Supplier;

class CDIJUnitExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {

    private SeContainer container;
    private CreationalContext<Object> context;

    @Override
    public void beforeAll(final ExtensionContext extensionContext) {
        final CDIExtension config = AnnotationUtils.findAnnotation(extensionContext.getElement(), CDIExtension.class)
                .orElse(null);
        if (config == null) {
            return;
        }
        Supplier<SeContainer> supplier = new ContainerSupplier(config);
        container = supplier.get();
    }

    @Override
    public void afterAll(final ExtensionContext extensionContext) {
        if (container != null) {
            doClose(container);
            container = null;
        }
    }

    @Override
    public void beforeEach(final ExtensionContext extensionContext) {
        if (container == null) {
            return;
        }
        extensionContext.getTestInstance().ifPresent(inject());
    }

    private Consumer<Object> inject() {
        return instance ->  {
            final BeanManager manager = container.getBeanManager();
            final AnnotatedType<Object> annotatedType = manager
                    .createAnnotatedType((Class<Object>) instance.getClass());
            final InjectionTarget<Object> injectionTarget = manager.createInjectionTarget(annotatedType);
            context = manager.createCreationalContext(null);
            injectionTarget.inject(instance, context);
        };
    }

    @Override
    public void afterEach(final ExtensionContext extensionContext) {
        if (context != null) {
            context.release();
            context = null;
        }
    }

    private void doClose(final SeContainer container) {
        container.close();
    }
}