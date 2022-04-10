package org.javamoney.jakarta.interceptor;

import org.javamoney.jakarta.Agenda;
import org.javamoney.jakarta.Store;
import org.javamoney.jakarta.StoreType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Objects;

@ApplicationScoped
public class AgendaLazyService {

    @Inject
    private Agenda agenda;

    @Store
    public void addNameLazy(String name) {
        Objects.requireNonNull(name, "name is required");
        agenda.add(name);
    }

    @Store(fields = "names")
    public void updateName(String name) {
        Objects.requireNonNull(name, "name is required");
        agenda.add(name);
    }

    @Store(value = StoreType.LAZY, root = true)
    public void addNameRoot(String name) {
        Objects.requireNonNull(name, "name is required");
        agenda.add(name);
    }
}
