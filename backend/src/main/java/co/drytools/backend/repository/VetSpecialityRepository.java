package co.drytools.backend.repository;

import co.drytools.backend.model.VetSpeciality;
import co.drytools.backend.model.id.VetSpecialityId;
import java.util.List;
import java.util.Optional;

public interface VetSpecialityRepository extends SimpleRepository<VetSpeciality, VetSpecialityId> {

    VetSpeciality create(String name, String description);

    Optional<VetSpeciality> findByName(String name);

    List<VetSpeciality> findByDescription(String description);
}
