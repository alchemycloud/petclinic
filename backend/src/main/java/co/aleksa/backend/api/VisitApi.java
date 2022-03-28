package co.aleksa.backend.api;

import co.aleksa.backend.api.dto.PagedDTO;
import co.aleksa.backend.api.dto.visitapi.CreateVisitRequest;
import co.aleksa.backend.api.dto.visitapi.CreateVisitResponse;
import co.aleksa.backend.api.dto.visitapi.DeleteVisitRequest;
import co.aleksa.backend.api.dto.visitapi.ReadVisitRequest;
import co.aleksa.backend.api.dto.visitapi.ReadVisitResponse;
import co.aleksa.backend.api.dto.visitapi.ScheduledVisitsRequest;
import co.aleksa.backend.api.dto.visitapi.UpdateVisitRequest;
import co.aleksa.backend.api.dto.visitapi.UpdateVisitResponse;
import co.aleksa.backend.api.dto.visitapi.VisitDTO;
import co.aleksa.backend.api.dto.visitapi.VisitsByPetRequest;
import co.aleksa.backend.api.dto.visitapi.VisitsByVetRequest;
import co.aleksa.backend.api.dto.visitapi.VisitsForOwnerRequest;
import co.aleksa.backend.model.Pet;
import co.aleksa.backend.model.User;
import co.aleksa.backend.model.Vet;
import co.aleksa.backend.model.Visit;
import co.aleksa.backend.model.enumeration.PetType;
import co.aleksa.backend.model.id.PetId;
import co.aleksa.backend.model.id.VetId;
import co.aleksa.backend.model.id.VisitId;
import co.aleksa.backend.repository.PetRepository;
import co.aleksa.backend.repository.VetRepository;
import co.aleksa.backend.repository.VisitRepository;
import co.aleksa.backend.repository.tuple.VisitFindScheduledVisitsTuple;
import co.aleksa.backend.repository.tuple.VisitFindVisitsByPetTuple;
import co.aleksa.backend.repository.tuple.VisitFindVisitsByVetTuple;
import co.aleksa.backend.repository.tuple.VisitFindVisitsForOwnerTuple;
import co.aleksa.backend.service.SecurityService;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VisitApi {
    private static final Logger LOG = LoggerFactory.getLogger(VisitApi.class);

    @Inject private VisitRepository visitRepository;

    @Inject private VetRepository vetRepository;

    @Inject private PetRepository petRepository;

    @Inject private SecurityService securityService;

    public ReadVisitResponse readVisit(ReadVisitRequest dto) {
        LOG.trace("readVisit {}", dto.getId());
        // TODO check security constraints(id)

        final Visit model = visitRepository.getOne(dto.getId());
        final VisitId responseId = model.getId();
        final VetId responseVetId = model.getVetId();
        final PetId responsePetId = model.getPetId();
        final Integer responseVisitNumber = model.getVisitNumber();
        final ZonedDateTime responseTimestamp = model.getTimestamp();
        final BigDecimal responsePetWeight = model.getPetWeight().orElse(null);
        final String responseDescription = model.getDescription().orElse(null);
        final Boolean responseScheduled = model.getScheduled();
        return new ReadVisitResponse(
                responseId, responseVetId, responsePetId, responseVisitNumber, responseTimestamp, responsePetWeight, responseDescription, responseScheduled);
    }

    public CreateVisitResponse createVisit(CreateVisitRequest dto) {
        LOG.trace("createVisit {} {}", dto.getVetId(), dto.getPetId());
        // TODO check security constraints(vetId, petId)

        final Vet vet = vetRepository.getOne(dto.getVetId());
        final Pet pet = petRepository.getOne(dto.getPetId());
        final Integer visitNumber = dto.getVisitNumber();
        final ZonedDateTime timestamp = dto.getTimestamp();
        final Optional<BigDecimal> petWeight = Optional.ofNullable(dto.getPetWeight());
        final Optional<String> description = Optional.ofNullable(dto.getDescription());
        final Boolean scheduled = dto.getScheduled();
        final Visit model = visitRepository.create(null, null, null, null, Optional.empty(), Optional.empty(), null);

        final VisitId responseId = model.getId();
        final VetId responseVetId = model.getVetId();
        final PetId responsePetId = model.getPetId();
        final Integer responseVisitNumber = model.getVisitNumber();
        final ZonedDateTime responseTimestamp = model.getTimestamp();
        final BigDecimal responsePetWeight = model.getPetWeight().orElse(null);
        final String responseDescription = model.getDescription().orElse(null);
        final Boolean responseScheduled = model.getScheduled();
        return new CreateVisitResponse(
                responseId, responseVetId, responsePetId, responseVisitNumber, responseTimestamp, responsePetWeight, responseDescription, responseScheduled);
    }

    public UpdateVisitResponse updateVisit(UpdateVisitRequest dto) {
        LOG.trace("updateVisit {} {} {}", dto.getId(), dto.getVetId(), dto.getPetId());
        // TODO check security constraints(id, vetId, petId)

        final Visit model = visitRepository.getOne(dto.getId());
        model.setVet(vetRepository.getOne(dto.getVetId()));
        model.setPet(petRepository.getOne(dto.getPetId()));
        model.setVisitNumber(dto.getVisitNumber());
        model.setTimestamp(dto.getTimestamp());
        model.setPetWeight(Optional.ofNullable(dto.getPetWeight()));
        model.setDescription(Optional.ofNullable(dto.getDescription()));
        model.setScheduled(dto.getScheduled());
        visitRepository.update(model);

        final VisitId responseId = model.getId();
        final VetId responseVetId = model.getVetId();
        final PetId responsePetId = model.getPetId();
        final Integer responseVisitNumber = model.getVisitNumber();
        final ZonedDateTime responseTimestamp = model.getTimestamp();
        final BigDecimal responsePetWeight = model.getPetWeight().orElse(null);
        final String responseDescription = model.getDescription().orElse(null);
        final Boolean responseScheduled = model.getScheduled();
        return new UpdateVisitResponse(
                responseId, responseVetId, responsePetId, responseVisitNumber, responseTimestamp, responsePetWeight, responseDescription, responseScheduled);
    }

    public void deleteVisit(DeleteVisitRequest dto) {
        LOG.trace("deleteVisit {}", dto.getId());
        // TODO check security constraints(id)

        visitRepository.deleteById(dto.getId());
    }

    public PagedDTO<VisitDTO> visitsByVet(VisitsByVetRequest dto) {
        LOG.trace("visitsByVet {}", dto.getVetId());
        // TODO check security constraints(vetId)

        final List<VisitFindVisitsByVetTuple> tuples = visitRepository.findVisitsByVet(dto.getVetId(), dto.getDrop(), dto.getTake());
        final Long totalCount = visitRepository.countVisitsByVet(dto.getVetId());
        final List<VisitDTO> dtos =
                tuples.stream()
                        .map(
                                tuple -> {
                                    final Integer responseVisitNumber = tuple.getVisit().getVisitNumber();
                                    final String responseDescription = tuple.getVisit().getDescription().orElse(null);
                                    final Boolean responseScheduled = tuple.getVisit().getScheduled();
                                    final String responseName = tuple.getPet().getName();
                                    final PetType responsePetType = tuple.getPet().getPetType();
                                    final String responseFirstName = null; // TODO set this field manually
                                    final String responseLastName = null; // TODO set this field manually
                                    return new VisitDTO(
                                            responseVisitNumber,
                                            responseDescription,
                                            responseScheduled,
                                            responseName,
                                            responsePetType,
                                            responseFirstName,
                                            responseLastName);
                                })
                        .toList();
        return new PagedDTO<>(dtos, totalCount);
    }

    public PagedDTO<VisitDTO> visitsByPet(VisitsByPetRequest dto) {
        LOG.trace("visitsByPet {}", dto.getPetId());
        // TODO check security constraints(petId)

        final List<VisitFindVisitsByPetTuple> tuples = visitRepository.findVisitsByPet(dto.getPetId(), dto.getDrop(), dto.getTake());
        final Long totalCount = visitRepository.countVisitsByPet(dto.getPetId());
        final List<VisitDTO> dtos =
                tuples.stream()
                        .map(
                                tuple -> {
                                    final Integer responseVisitNumber = tuple.getVisit().getVisitNumber();
                                    final String responseDescription = tuple.getVisit().getDescription().orElse(null);
                                    final Boolean responseScheduled = tuple.getVisit().getScheduled();
                                    final String responseName = null; // TODO set this field manually
                                    final PetType responsePetType = null; // TODO set this field manually
                                    final String responseFirstName = tuple.getUser().getFirstName();
                                    final String responseLastName = tuple.getUser().getLastName();
                                    return new VisitDTO(
                                            responseVisitNumber,
                                            responseDescription,
                                            responseScheduled,
                                            responseName,
                                            responsePetType,
                                            responseFirstName,
                                            responseLastName);
                                })
                        .toList();
        return new PagedDTO<>(dtos, totalCount);
    }

    public PagedDTO<VisitDTO> scheduledVisits(ScheduledVisitsRequest dto) {
        LOG.trace("scheduledVisits");
        // TODO check security constraints

        final User principal = securityService.getPrincipal().get();

        final List<VisitFindScheduledVisitsTuple> tuples = visitRepository.findScheduledVisits(principal.getId(), dto.getDrop(), dto.getTake());
        final Long totalCount = visitRepository.countScheduledVisits(principal.getId());
        final List<VisitDTO> dtos =
                tuples.stream()
                        .map(
                                tuple -> {
                                    final Integer responseVisitNumber = tuple.getVisit().getVisitNumber();
                                    final String responseDescription = tuple.getVisit().getDescription().orElse(null);
                                    final Boolean responseScheduled = tuple.getVisit().getScheduled();
                                    final String responseName = null; // TODO set this field manually
                                    final PetType responsePetType = null; // TODO set this field manually
                                    final String responseFirstName = tuple.getUser().getFirstName();
                                    final String responseLastName = tuple.getUser().getLastName();
                                    return new VisitDTO(
                                            responseVisitNumber,
                                            responseDescription,
                                            responseScheduled,
                                            responseName,
                                            responsePetType,
                                            responseFirstName,
                                            responseLastName);
                                })
                        .toList();
        return new PagedDTO<>(dtos, totalCount);
    }

    public PagedDTO<VisitDTO> visitsForOwner(VisitsForOwnerRequest dto) {
        LOG.trace("visitsForOwner");
        // TODO check security constraints

        final User principal = securityService.getPrincipal().get();

        final List<VisitFindVisitsForOwnerTuple> tuples = visitRepository.findVisitsForOwner(principal.getId(), dto.getDrop(), dto.getTake());
        final Long totalCount = visitRepository.countVisitsForOwner(principal.getId());
        final List<VisitDTO> dtos =
                tuples.stream()
                        .map(
                                tuple -> {
                                    final Integer responseVisitNumber = tuple.getVisit().getVisitNumber();
                                    final String responseDescription = tuple.getVisit().getDescription().orElse(null);
                                    final Boolean responseScheduled = tuple.getVisit().getScheduled();
                                    final String responseName = null; // TODO set this field manually
                                    final PetType responsePetType = null; // TODO set this field manually
                                    final String responseFirstName = tuple.getUser().getFirstName();
                                    final String responseLastName = tuple.getUser().getLastName();
                                    return new VisitDTO(
                                            responseVisitNumber,
                                            responseDescription,
                                            responseScheduled,
                                            responseName,
                                            responsePetType,
                                            responseFirstName,
                                            responseLastName);
                                })
                        .toList();
        return new PagedDTO<>(dtos, totalCount);
    }
}
