package co.aleksa.backend.repository;

import co.aleksa.backend.model.Pet;
import co.aleksa.backend.model.PetHistory;
import co.aleksa.backend.model.enumeration.PetType;
import co.aleksa.backend.model.id.PetHistoryId;
import co.aleksa.backend.model.id.PetId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public interface PetHistoryRepository extends SimpleRepository<PetHistory, PetHistoryId> {

    PetHistory create(
            Optional<String> correlationId,
            Long entityId,
            Optional<Pet> reference,
            ZonedDateTime changeTime,
            Optional<PetHistory> previous,
            Optional<Long> ownerId,
            Optional<String> name,
            Optional<ZonedDateTime> birthday,
            Optional<PetType> petType,
            Optional<Boolean> vaccinated);

    PetHistory getLastHistoryFor(PetId reference);

    List<Pair<Pet, Optional<PetHistory>>> findAllWithLastHistory(Set<PetId> ids);

    List<PetHistory> findAllHistoriesOfEntities(List<PetId> referenceId);

    List<PetHistory> findByCorrelationId(Optional<String> correlationId);

    List<PetHistory> findByCorrelationIdMandatory(String correlationId);

    List<PetHistory> findByEntityId(Long entityId);

    List<PetHistory> findByChangeTime(ZonedDateTime changeTime);

    List<PetHistory> findByOwnerId(Optional<Long> ownerId);

    List<PetHistory> findByOwnerIdMandatory(Long ownerId);

    List<PetHistory> findByName(Optional<String> name);

    List<PetHistory> findByNameMandatory(String name);

    List<PetHistory> findByBirthday(Optional<ZonedDateTime> birthday);

    List<PetHistory> findByBirthdayMandatory(ZonedDateTime birthday);

    List<PetHistory> findByPetType(Optional<PetType> petType);

    List<PetHistory> findByPetTypeMandatory(PetType petType);

    List<PetHistory> findByVaccinated(Optional<Boolean> vaccinated);

    List<PetHistory> findByVaccinatedMandatory(Boolean vaccinated);
}
