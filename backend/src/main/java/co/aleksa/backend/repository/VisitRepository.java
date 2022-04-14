package co.aleksa.backend.repository;

import co.aleksa.backend.model.Pet;
import co.aleksa.backend.model.Vet;
import co.aleksa.backend.model.Visit;
import co.aleksa.backend.model.id.PetId;
import co.aleksa.backend.model.id.UserId;
import co.aleksa.backend.model.id.VetId;
import co.aleksa.backend.model.id.VisitId;
import co.aleksa.backend.repository.tuple.VisitFindScheduledVisitsTuple;
import co.aleksa.backend.repository.tuple.VisitFindVisitsByPetTuple;
import co.aleksa.backend.repository.tuple.VisitFindVisitsByVetTuple;
import co.aleksa.backend.repository.tuple.VisitFindVisitsForOwnerTuple;
import co.aleksa.backend.repository.tuple.VisitScheduledVisitsTuple;
import co.aleksa.backend.repository.tuple.VisitVisitsByPetTuple;
import co.aleksa.backend.repository.tuple.VisitVisitsByVetTuple;
import co.aleksa.backend.repository.tuple.VisitVisitsForOwnerTuple;
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

    List<VisitVisitsByVetTuple> visitsByVet(VetId vetId);

    List<VisitFindVisitsByVetTuple> findVisitsByVet(VetId vetId, Integer drop, Integer take);

    Long countVisitsByVet(VetId vetId);

    List<VisitVisitsByPetTuple> visitsByPet(PetId petId);

    List<VisitFindVisitsByPetTuple> findVisitsByPet(PetId petId, Integer drop, Integer take);

    Long countVisitsByPet(PetId petId);

    List<VisitScheduledVisitsTuple> scheduledVisits(UserId principalId);

    List<VisitFindScheduledVisitsTuple> findScheduledVisits(UserId principalId, Integer drop, Integer take);

    Long countScheduledVisits(UserId principalId);

    List<VisitVisitsForOwnerTuple> visitsForOwner(UserId principalId);

    List<VisitFindVisitsForOwnerTuple> findVisitsForOwner(UserId principalId, Integer drop, Integer take);

    Long countVisitsForOwner(UserId principalId);
}
