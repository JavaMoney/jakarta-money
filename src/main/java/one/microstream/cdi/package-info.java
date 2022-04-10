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
 * MicroStream Data-Store is a native Java object graph storage engine. From a technical
 * point of view it serves one purpose only:
 *
 * MicroStream Data-Store is a storage engine, but no database
 * management system (DBMS). Many features that typical DBMS
 * provide have been left out on purpose. The reason is that
 * those features exist to make a DBMS something of a server
 * application platform of an "old kind" on top of its data
 * store functionality: A standalone process with user
 * management, connection management, session handling,
 * often even with a programming language of its own,
 * a querying interface (SQL), etc.
 * Today, all of those server application features
 * are already and much better handled by dedicated
 * server applications (the "new kind"), implemented in
 * a modern language like Java. They have their built-in user,
 * connection and session management, the querying interface
 * to the outside world are typically web services instead of SQL,
 * etc.
 * But those modern server applications still lack
 * one important thing: an easy to use and technically
 * efficient way to store and restore their application’s data.
 * So a "new kind" server often uses an "old kind" server just
 * to do the data storing.
 * This comes at the price of catching all the overhead
 * and problems of redundant user,
 * connection and session management
 * AND the outdated concepts and limitations of
 * the old querying interface (SQL).
 * Isn’t that very weird and frustratingly complicated?
 * Why not simply include a modern data storing library
 * in the modern server and be done with it?
 * A storing library that perfectly fits the modern technology
 * and brings in no redundant overhead or complication of
 * a secondary outdated wannabe server process.
 */
package one.microstream.cdi;