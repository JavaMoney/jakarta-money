
  
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

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * The CDI extension to work with JUnit 5 Jupiter.
 */
@Target(TYPE)
@Retention(RUNTIME)
@ExtendWith(CDIJUnitExtension.class)
public @interface CDIExtension {
    /**
     * @return classes to deploy.
     */
    Class<?>[] classes() default {};

    /**
     * @return decorators to activate.
     */
    Class<?>[] decorators() default {};

    /**
     * @return interceptors to activate.
     */
    Class<?>[] interceptors() default {};

    /**
     * @return alternatives to activate.
     */
    Class<?>[] alternatives() default {};

    /**
     * @return stereotypes to activate.
     */
    Class<? extends Annotation>[] alternativeStereotypes() default {};

    /**
     * @return packages to deploy.
     */
    Class<?>[] packages() default {};

    /**
     * @return packages to deploy recursively.
     */
    Class<?>[] recursivePackages() default {};

    /**
     * @return if the automatic scanning must be disabled.
     */
    boolean disableDiscovery() default false;

}