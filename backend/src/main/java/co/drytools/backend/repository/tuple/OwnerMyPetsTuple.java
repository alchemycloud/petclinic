package co.drytools.backend.repository.tuple;

import co.drytools.backend.model.Owner;
import co.drytools.backend.model.Pet;
import co.drytools.backend.model.User;

public class OwnerMyPetsTuple {

    private final Owner owner;
    private final Pet pet;
    private final User user;

    public OwnerMyPetsTuple(Owner owner, Pet pet, User user) {
        this.owner = owner;
        this.pet = pet;
        this.user = user;
    }

    public Owner getOwner() {
        return owner;
    }

    public Pet getPet() {
        return pet;
    }

    public User getUser() {
        return user;
    }
}
