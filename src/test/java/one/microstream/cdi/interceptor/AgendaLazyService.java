package one.microstream.cdi.interceptor;

import one.microstream.cdi.Agenda;
import one.microstream.cdi.Store;
import one.microstream.cdi.StoreType;

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
