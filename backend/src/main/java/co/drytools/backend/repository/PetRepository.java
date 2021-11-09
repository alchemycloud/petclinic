package co.drytools.backend.repository;

import co.drytools.backend.model.Owner;
import co.drytools.backend.model.Pet;
import co.drytools.backend.model.enumeration.PetType;
import co.drytools.backend.model.id.OwnerId;
import co.drytools.backend.model.id.PetId;
import co.drytools.backend.repository.tuple.PetFindPetsTuple;
import co.drytools.backend.repository.tuple.PetPetWithOwnerForOwnerTuple;
import co.drytools.backend.repository.tuple.PetPetsTuple;
import java.time.ZonedDateTime;
import java.util.List;

public interface PetRepository extends SimpleRepository<Pet, PetId> {

    Pet create(Owner owner, String name, ZonedDateTime birthdate, PetType petType, Boolean vaccinated);

    Long petWithOwnerCount();

    List<PetPetWithOwnerForOwnerTuple> petWithOwnerForOwner(OwnerId ownerId);

    List<Pet> findByName(String name);

    List<Pet> findByBirthdate(ZonedDateTime birthdate);

    List<Pet> findByPetType(PetType petType);

    List<Pet> findByVaccinated(Boolean vaccinated);

    List<PetPetsTuple> pets();

    List<PetFindPetsTuple> findPets(Integer drop, Integer take);

    Long countPets();

    List<Pet> findPetbyType(PetType petType);
}
