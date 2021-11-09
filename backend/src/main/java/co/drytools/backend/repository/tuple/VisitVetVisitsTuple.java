package co.drytools.backend.repository.tuple;

import co.drytools.backend.model.Pet;
import co.drytools.backend.model.Vet;
import co.drytools.backend.model.Visit;

public class VisitVetVisitsTuple {

    private final Visit visit;
    private final Vet vet;
    private final Pet pet;

    public VisitVetVisitsTuple(Visit visit, Vet vet, Pet pet) {
        this.visit = visit;
        this.vet = vet;
        this.pet = pet;
    }

    public Visit getVisit() {
        return visit;
    }

    public Vet getVet() {
        return vet;
    }

    public Pet getPet() {
        return pet;
    }
}
