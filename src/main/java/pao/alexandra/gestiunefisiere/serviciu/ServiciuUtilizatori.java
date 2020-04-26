package pao.alexandra.gestiunefisiere.serviciu;

import pao.alexandra.gestiunefisiere.model.utilizator.Utilizator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServiciuUtilizatori {

    private Map<UUID, Utilizator> utilizatori = new HashMap<>();

    public UUID adaugaUtilizator(UUID id, Utilizator utilizator) {
        utilizatori.put(id, utilizator);
        return id;
    }
}
