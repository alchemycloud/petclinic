package co.drytools.backend.repository.tuple;

import co.drytools.backend.model.Owner;
import co.drytools.backend.model.Pet;
import java.util.Optional;

public class PetPetWithOwnerForOwnerTuple {

    private final Pet pet;
    private final Optional<Owner> owner;

    public PetPetWithOwnerForOwnerTuple(Pet pet, Optional<Owner> owner) {
        this.pet = pet;
        this.owner = owner;
    }

    public Pet getPet() {
        return pet;
    }

    public Optional<Owner> getOwner() {
        return owner;
    }
}
