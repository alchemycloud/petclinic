package co.aleksa.backend.repository.tuple;

import co.aleksa.backend.model.Owner;
import co.aleksa.backend.model.Pet;
import co.aleksa.backend.model.User;
import co.aleksa.backend.model.Vet;
import co.aleksa.backend.model.Visit;

public class VisitFindVisitsForOwnerTuple {

    private final Visit visit;
    private final Pet pet;
    private final Vet vet;
    private final User user;
    private final Owner owner;

    public VisitFindVisitsForOwnerTuple(Visit visit, Pet pet, Vet vet, User user, Owner owner) {
        this.visit = visit;
        this.pet = pet;
        this.vet = vet;
        this.user = user;
        this.owner = owner;
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

    public User getUser() {
        return user;
    }

    public Owner getOwner() {
        return owner;
    }
}
