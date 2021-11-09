package co.drytools.backend.repository;

import co.drytools.backend.model.Vet;
import co.drytools.backend.model.VetSpecialities;
import co.drytools.backend.model.VetSpeciality;
import co.drytools.backend.model.id.VetSpecialitiesId;

public interface VetSpecialitiesRepository extends SimpleRepository<VetSpecialities, VetSpecialitiesId> {

    VetSpecialities create(Vet vet, VetSpeciality speciality);
}
