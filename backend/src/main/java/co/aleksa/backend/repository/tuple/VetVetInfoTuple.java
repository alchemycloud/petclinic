package co.aleksa.backend.repository.tuple;

import co.aleksa.backend.model.Vet;
import co.aleksa.backend.model.VetSpecialty;
import java.util.List;

public class VetVetInfoTuple {

    private final Vet vet;
    private final List<VetSpecialty> vetSpecialty;

    public VetVetInfoTuple(Vet vet, List<VetSpecialty> vetSpecialty) {
        this.vet = vet;
        this.vetSpecialty = vetSpecialty;
    }

    public Vet getVet() {
        return vet;
    }

    public List<VetSpecialty> getVetSpecialty() {
        return vetSpecialty;
    }
}
