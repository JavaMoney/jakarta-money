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
import one.microstream.concurrency.XThreads;
import one.microstream.storage.types.StorageManager;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Optional.ofNullable;

@Store
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
class StoreInterceptor {

    private static final Logger LOGGER = Logger.getLogger(StoreInterceptor.class.getName());

    @Inject
    private StorageManager manager;

    @Inject
    private StorageExtension extension;

    @AroundInvoke
    public Object store(InvocationContext context) throws Exception {

        final Store store = ofNullable(context.getMethod().getAnnotation(Store.class))
                .orElse(context.getMethod().getDeclaringClass().getAnnotation(Store.class));
        LOGGER.log(Level.FINEST, "Using Store operation in the " + context.getMethod()
                + " using the store type: " + store.value());

        Object result = context.proceed();
        XThreads.executeSynchronized(() -> {
            StoreStrategy strategy = StoreStrategy.of(store);
            strategy.store(store, manager, extension);
        });

        return result;
    }
}
