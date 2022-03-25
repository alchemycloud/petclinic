package co.aleksa.backend.repository.tuple;

import co.aleksa.backend.model.Owner;
import co.aleksa.backend.model.Pet;
import co.aleksa.backend.model.User;
import co.aleksa.backend.model.Vet;
import co.aleksa.backend.model.Visit;

public class VisitVisitsByPetTuple {

    private final Visit visit;
    private final Pet pet;
    private final Vet vet;
    private final Owner owner;
    private final User user;

    public VisitVisitsByPetTuple(Visit visit, Pet pet, Vet vet, Owner owner, User user) {
        this.visit = visit;
        this.pet = pet;
        this.vet = vet;
        this.owner = owner;
        this.user = user;
    }

    public Visit getVisit() {
        return visit;
    }

    public Pet getPet() {
        return pet;
    }

    public Vet getVet() {
        return vet;
    }

    public Owner getOwner() {
        return owner;
    }

    public User getUser() {
        return user;
    }
}
