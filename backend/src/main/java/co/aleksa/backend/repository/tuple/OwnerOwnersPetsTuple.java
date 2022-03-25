package co.aleksa.backend.repository.tuple;

import co.aleksa.backend.model.Owner;
import co.aleksa.backend.model.Pet;

public class OwnerOwnersPetsTuple {

    private final Owner owner;
    private final Pet pet;

    public OwnerOwnersPetsTuple(Owner owner, Pet pet) {
        this.owner = owner;
        this.pet = pet;
    }

    public Owner getOwner() {
        return owner;
    }

    public Pet getPet() {
        return pet;
    }
}
