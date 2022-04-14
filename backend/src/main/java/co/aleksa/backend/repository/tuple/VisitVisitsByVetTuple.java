package co.aleksa.backend.repository.tuple;

import co.aleksa.backend.model.Pet;
import co.aleksa.backend.model.Vet;
import co.aleksa.backend.model.Visit;

public class VisitVisitsByVetTuple {

    private final Visit visit;
    private final Vet vet;
    private final Pet pet;

    public VisitVisitsByVetTuple(Visit visit, Vet vet, Pet pet) {
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
