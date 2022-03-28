package co.aleksa.backend.repository;

import co.aleksa.backend.model.VetSpecialty;
import co.aleksa.backend.model.id.VetSpecialtyId;
import java.util.List;
import java.util.Optional;

public interface VetSpecialtyRepository extends SimpleRepository<VetSpecialty, VetSpecialtyId> {

    VetSpecialty create(String name, String description);

    Optional<VetSpecialty> findByName(String name);

    List<VetSpecialty> findByDescription(String description);
}
