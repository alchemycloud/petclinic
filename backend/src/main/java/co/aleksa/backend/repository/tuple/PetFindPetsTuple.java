package co.aleksa.backend.repository.tuple;

import co.aleksa.backend.model.Owner;
import co.aleksa.backend.model.Pet;
import co.aleksa.backend.model.User;

public class PetFindPetsTuple {

    private final Pet pet;
    private final Owner owner;
    private final User user;

    public PetFindPetsTuple(Pet pet, Owner owner, User user) {
        this.pet = pet;
        this.owner = owner;
        this.user = user;
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
