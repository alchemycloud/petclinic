package co.drytools.backend.api;

import co.drytools.backend.api.dto.PagedDTO;
import co.drytools.backend.api.dto.visitapi.CreateVisitRequest;
import co.drytools.backend.api.dto.visitapi.CreateVisitResponse;
import co.drytools.backend.api.dto.visitapi.DeleteVisitRequest;
import co.drytools.backend.api.dto.visitapi.MyVisitsRequest;
import co.drytools.backend.api.dto.visitapi.MyVisitsResponse;
import co.drytools.backend.api.dto.visitapi.ReadVisitRequest;
import co.drytools.backend.api.dto.visitapi.ReadVisitResponse;
import co.drytools.backend.api.dto.visitapi.ScheduledVisitsResponse;
import co.drytools.backend.api.dto.visitapi.UpdateVisitRequest;
import co.drytools.backend.api.dto.visitapi.UpdateVisitResponse;
import co.drytools.backend.api.dto.visitapi.VetVisitsRequest;
import co.drytools.backend.api.dto.visitapi.VetVisitsResponse;
import co.drytools.backend.model.Pet;
import co.drytools.backend.model.Vet;
import co.drytools.backend.model.Visit;
import co.drytools.backend.model.enumeration.PetType;
import co.drytools.backend.model.enumeration.UserRole;
import co.drytools.backend.model.id.OwnerId;
import co.drytools.backend.model.id.PetId;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.model.id.VetId;
import co.drytools.backend.model.id.VisitId;
import co.drytools.backend.repository.PetRepository;
import co.drytools.backend.repository.VetRepository;
import co.drytools.backend.repository.VisitRepository;
import co.drytools.backend.repository.tuple.VisitFindVetVisitsTuple;
import co.drytools.backend.repository.tuple.VisitMyVisitsTuple;
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

    public PagedDTO<VetVisitsResponse> vetVisits(VetVisitsRequest dto) {
        LOG.trace("vetVisits {}", dto.getUserId());
        // TODO check security constraints(userId)

        final List<VisitFindVetVisitsTuple> tuples = visitRepository.findVetVisits(dto.getUserId(), dto.getDrop(), dto.getTake());
        final Long totalCount = visitRepository.countVetVisits(dto.getUserId());
        final List<VetVisitsResponse> dtos =
                tuples.stream()
                        .map(
                                tuple -> {
                                    final UserId responseVetUserId = tuple.getVet().getUserId();
                                    final String responsePetName = tuple.getPet().getName();
                                    final Integer responseVisitNumber = tuple.getVisit().getVisitNumber();
                                    final Boolean responseScheduled = tuple.getVisit().getScheduled();
                                    return new VetVisitsResponse(responseVetUserId, responsePetName, responseVisitNumber, responseScheduled);
                                })
                        .toList();
        return new PagedDTO<>(dtos, totalCount);
    }

    public List<ScheduledVisitsResponse> scheduledVisits() {
        LOG.trace("scheduledVisits");
        // TODO check security constraints

        final List<Visit> models = visitRepository.scheduledVisits();
        return models.stream()
                .map(
                        model -> {
                            final VisitId responseId = model.getId();
                            final VetId responseVetId = model.getVetId();
                            final PetId responsePetId = model.getPetId();
                            final Integer responseVisitNumber = model.getVisitNumber();
                            final ZonedDateTime responseTimestamp = model.getTimestamp();
                            final BigDecimal responsePetWeight = model.getPetWeight().orElse(null);
                            final String responseDescription = model.getDescription().orElse(null);
                            final Boolean responseScheduled = model.getScheduled();
                            return new ScheduledVisitsResponse(
                                    responseId,
                                    responseVetId,
                                    responsePetId,
                                    responseVisitNumber,
                                    responseTimestamp,
                                    responsePetWeight,
                                    responseDescription,
                                    responseScheduled);
                        })
                .toList();
    }

    public List<MyVisitsResponse> myVisits(MyVisitsRequest dto) {
        LOG.trace("myVisits {}", dto.getUserIdId());
        // TODO check security constraints(userIdId)

        final List<VisitMyVisitsTuple> tuples = visitRepository.myVisits(dto.getUserIdId());
        return tuples.stream()
                .map(
                        tuple -> {
                            final VisitId responseId = tuple.getVisit().getId();
                            final VetId responseVetId = tuple.getVisit().getVetId();
                            final PetId responsePetId = tuple.getVisit().getPetId();
                            final Integer responseVisitNumber = tuple.getVisit().getVisitNumber();
                            final ZonedDateTime responseTimestamp = tuple.getVisit().getTimestamp();
                            final BigDecimal responsePetWeight = tuple.getVisit().getPetWeight().orElse(null);
                            final String responseDescription = tuple.getVisit().getDescription().orElse(null);
                            final Boolean responseScheduled = tuple.getVisit().getScheduled();
                            final OwnerId responsePetOwnerId = tuple.getPet().getOwnerId();
                            final String responsePetName = tuple.getPet().getName();
                            final ZonedDateTime responsePetBirthdate = tuple.getPet().getBirthdate();
                            final PetType responsePetPetType = tuple.getPet().getPetType();
                            final Boolean responsePetVaccinated = tuple.getPet().getVaccinated();
                            final OwnerId responseOwnerId = tuple.getOwner().getId();
                            final UserId responseOwnerUserId = tuple.getOwner().getUserId();
                            final String responseOwnerAddress = tuple.getOwner().getAddress().orElse(null);
                            final String responseOwnerCity = tuple.getOwner().getCity().orElse(null);
                            final String responseOwnerTelephone = tuple.getOwner().getTelephone().orElse(null);
                            final UserId responseUserId = tuple.getUser().getId();
                            final String responseUserFirstName = tuple.getUser().getFirstName();
                            final String responseUserLastName = tuple.getUser().getLastName();
                            final ZonedDateTime responseUserBirthdate = tuple.getUser().getBirthdate();
                            final Boolean responseUserActive = tuple.getUser().getActive();
                            final UserRole responseUserRole = tuple.getUser().getRole();
                            final String responseUserEmail = tuple.getUser().getEmail();
                            final String responseUserPasswordHash = tuple.getUser().getPasswordHash();
                            final String responseUserEmailVerificationCode = tuple.getUser().getEmailVerificationCode().orElse(null);
                            final ZonedDateTime responseUserEmailVerificationCodeTimestamp = tuple.getUser().getEmailVerificationCodeTimestamp().orElse(null);
                            final Boolean responseUserEmailVerified = tuple.getUser().getEmailVerified();
                            final String responseUserResetPasswordCode = tuple.getUser().getResetPasswordCode().orElse(null);
                            final ZonedDateTime responseUserResetPasswordCodeTimestamp = tuple.getUser().getResetPasswordCodeTimestamp().orElse(null);
                            return new MyVisitsResponse(
                                    responseId,
                                    responseVetId,
                                    responsePetId,
                                    responseVisitNumber,
                                    responseTimestamp,
                                    responsePetWeight,
                                    responseDescription,
                                    responseScheduled,
                                    responsePetOwnerId,
                                    responsePetName,
                                    responsePetBirthdate,
                                    responsePetPetType,
                                    responsePetVaccinated,
                                    responseOwnerId,
                                    responseOwnerUserId,
                                    responseOwnerAddress,
                                    responseOwnerCity,
                                    responseOwnerTelephone,
                                    responseUserId,
                                    responseUserFirstName,
                                    responseUserLastName,
                                    responseUserBirthdate,
                                    responseUserActive,
                                    responseUserRole,
                                    responseUserEmail,
                                    responseUserPasswordHash,
                                    responseUserEmailVerificationCode,
                                    responseUserEmailVerificationCodeTimestamp,
                                    responseUserEmailVerified,
                                    responseUserResetPasswordCode,
                                    responseUserResetPasswordCodeTimestamp);
                        })
                .toList();
    }
}
