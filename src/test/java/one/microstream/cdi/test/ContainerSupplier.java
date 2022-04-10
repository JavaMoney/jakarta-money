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

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.function.Supplier;
import java.util.stream.Stream;

class ContainerSupplier implements Supplier<SeContainer> {

    private final CDIExtension config;

    ContainerSupplier(CDIExtension config) {
        this.config = config;
    }

    @Override
    public SeContainer get() {
        final SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        if (config.disableDiscovery()) {
            initializer.disableDiscovery();
        }
        initializer.setClassLoader(Thread.currentThread().getContextClassLoader());
        initializer.addBeanClasses(config.classes());
        initializer.enableDecorators(config.decorators());
        initializer.enableInterceptors(config.interceptors());
        initializer.selectAlternatives(config.alternatives());
        initializer.selectAlternativeStereotypes(config.alternativeStereotypes());
        initializer.addPackages(getPackages(config.packages()));
        initializer.addPackages(true, getPackages(config.recursivePackages()));
        return initializer.initialize();
    }

    private Package[] getPackages(Class<?>[] packages) {
        return Stream.of(packages).map(Class::getPackage).toArray(Package[]::new);
    }


}