package one.microstream.cdi.interceptor;

import one.microstream.cdi.Agenda;
import one.microstream.cdi.Store;
import one.microstream.cdi.StoreType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Objects;

@ApplicationScoped
@Store
public class AgendaEagerService {

    @Inject
    private Agenda agenda;

    @Store(StoreType.EAGER)
    public void add(String name) {
        Objects.requireNonNull(name, "name is required");
        agenda.add(name);
    }

    @Store(value = StoreType.EAGER, root = true)
    public void addEager(String name) {
        Objects.requireNonNull(name, "name is required");
        agenda.add(name);
    }

    @Store(value = StoreType.EAGER, fields = "names")
    public void updateName(String name) {
        Objects.requireNonNull(name, "name is required");
        agenda.add(name);
    }
}
