package co.drytools.backend.repository.tuple;

import co.drytools.backend.model.Owner;
import co.drytools.backend.model.Pet;
import co.drytools.backend.model.User;
import co.drytools.backend.model.Visit;

public class VisitMyVisitsTuple {

    private final Visit visit;
    private final Pet pet;
    private final Owner owner;
    private final User user;

    public VisitMyVisitsTuple(Visit visit, Pet pet, Owner owner, User user) {
        this.visit = visit;
        this.pet = pet;
        this.owner = owner;
        this.user = user;
    }

    public Visit getVisit() {
        return visit;
    }

    public Pet getPet() {
        return pet;
    }

    public Owner getOwner() {
        return owner;
    }

    public User getUser() {
        return user;
    }
}
