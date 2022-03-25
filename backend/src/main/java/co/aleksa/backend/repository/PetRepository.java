package co.aleksa.backend.repository;

import co.aleksa.backend.model.Owner;
import co.aleksa.backend.model.Pet;
import co.aleksa.backend.model.enumeration.PetType;
import co.aleksa.backend.model.id.PetId;
import co.aleksa.backend.repository.tuple.PetFindPetsTuple;
import co.aleksa.backend.repository.tuple.PetPetsTuple;
import java.time.ZonedDateTime;
import java.util.List;

public interface PetRepository extends SimpleRepository<Pet, PetId> {

    Pet create(Owner owner, String name, ZonedDateTime birthday, PetType petType, Boolean vaccinated);

    List<Pet> findByName(String name);

    List<Pet> findByBirthday(ZonedDateTime birthday);

    List<Pet> findByPetType(PetType petType);

    List<Pet> findByVaccinated(Boolean vaccinated);

    List<PetPetsTuple> pets();

    List<PetFindPetsTuple> findPets(Integer drop, Integer take);

    Long countPets();

    List<Pet> petsByType(PetType petType);
}
