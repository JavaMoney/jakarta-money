/*
 *    Copyright 2022 Otavio Santana
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

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Storage
public class Agenda {

    private final Set<String> names;

    public Agenda() {
        this.names =  new ConcurrentSkipListSet<>();
    }

    public void add(String name) {
        this.names.add(name);
    }

    public Set<String> getNames() {
        return Collections.unmodifiableSet(names);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Agenda agenda = (Agenda) o;
        return Objects.equals(this.names, agenda.names);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(names);
    }

    @Override
    public String toString() {
        return "Agenda{" +
                "names=" + names +
                '}';
    }
}
