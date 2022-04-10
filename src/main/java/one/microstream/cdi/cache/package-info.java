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

/**
 * Since version 3 MicroStream provides a JCache (JSR-107) implementation, which is optionally backed
 * by the MicroStream Storage.
 *
 * JCache standardizes caching for the Java platform. It provides a common mechanism to cache values in
 * a map-like structure. It expedites the mainstream adoption of in-memory computing by giving all
 * Java developers an easy way to access memory from within Java. Businesses can change providers
 * without rewriting their applications or maintaining a proprietary cache abstraction layer.
 *
 * Ref: https://docs.microstream.one/manual/cache/index.html
 */
package one.microstream.cdi.cache;