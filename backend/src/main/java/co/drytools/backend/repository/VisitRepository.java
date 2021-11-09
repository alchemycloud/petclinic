package co.drytools.backend.repository;

import co.drytools.backend.model.Pet;
import co.drytools.backend.model.Vet;
import co.drytools.backend.model.Visit;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.model.id.VisitId;
import co.drytools.backend.repository.tuple.VisitFindVetVisitsTuple;
import co.drytools.backend.repository.tuple.VisitMyVisitsTuple;
import co.drytools.backend.repository.tuple.VisitVetVisitsTuple;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface VisitRepository extends SimpleRepository<Visit, VisitId> {

    Visit create(
            Vet vet, Pet pet, Integer visitNumber, ZonedDateTime timestamp, Optional<BigDecimal> petWeight, Optional<String> description, Boolean scheduled);

    List<Visit> findByVisitNumber(Integer visitNumber);

    List<Visit> findByTimestamp(ZonedDateTime timestamp);

    List<Visit> findByPetWeight(Optional<BigDecimal> petWeight);

    List<Visit> findByPetWeightMandatory(BigDecimal petWeight);

    List<Visit> findByDescription(Optional<String> description);

    List<Visit> findByDescriptionMandatory(String description);

    List<Visit> findByScheduled(Boolean scheduled);

    List<VisitVetVisitsTuple> vetVisits(UserId userId);

    List<VisitFindVetVisitsTuple> findVetVisits(UserId userId, Integer drop, Integer take);

    Long countVetVisits(UserId userId);

    List<Visit> scheduledVisits();

    List<VisitMyVisitsTuple> myVisits(UserId userIdId);
}
