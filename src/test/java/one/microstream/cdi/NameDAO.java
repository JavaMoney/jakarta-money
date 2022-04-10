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

import one.microstream.storage.types.StorageManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class NameDAO {

    @Inject
    private StorageManager manager;

    private Agenda root;

    @PostConstruct
    public void init() {
        if (Objects.isNull(manager.root())) {
            this.root = new Agenda();
            manager.setRoot(root);
        } else {
            this.root = (Agenda) manager.root();
        }
    }

    public void add(String name) {
        root.add(Objects.requireNonNull(name, "name is required"));
    }

    public List<String> getNamesSorted() {
        return root.getNames().stream().sorted().collect(Collectors.toList());
    }
}
