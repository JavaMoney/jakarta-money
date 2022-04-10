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
package one.microstream.cdi;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Stereotype;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Storage class in Microstream indicates a class as a root instance.
 * Object instances can be stored as simple records.
 * One value after another as a trivial byte stream.
 * References between objects are mapped with unique numbers, called ObjectId, or short OID. + With both combined,
 * byte streams and OIDs, an object graph can be stored in a simple and quick way,
 * as well as loaded, as a whole or partially.
 * Ref: https://docs.microstream.one/manual/storage/root-instances.html
 *
 * Each application must have a unique class with this annotation.
 * Note: To increase performance use immutable sub-graphs as often as possible.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Stereotype
@Alternative
public @interface Storage {
}
