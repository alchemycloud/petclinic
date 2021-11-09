package co.drytools.backend.api;

import co.drytools.backend.api.dto.PagedDTO;
import co.drytools.backend.api.dto.ownerapi.AllOwnersRequest;
import co.drytools.backend.api.dto.ownerapi.AllOwnersResponse;
import co.drytools.backend.api.dto.ownerapi.CreateOwnerRequest;
import co.drytools.backend.api.dto.ownerapi.CreateOwnerResponse;
import co.drytools.backend.api.dto.ownerapi.DeleteOwnerRequest;
import co.drytools.backend.api.dto.ownerapi.MyPetsResponse;
import co.drytools.backend.api.dto.ownerapi.OwnerVetsResponse;
import co.drytools.backend.api.dto.ownerapi.OwnersForAddressRequest;
import co.drytools.backend.api.dto.ownerapi.OwnersForAddressResponse;
import co.drytools.backend.api.dto.ownerapi.OwnersPetsRequest;
import co.drytools.backend.api.dto.ownerapi.OwnersPetsResponse;
import co.drytools.backend.api.dto.ownerapi.OwnersWithPetsResponse;
import co.drytools.backend.api.dto.ownerapi.ReadOwnerRequest;
import co.drytools.backend.api.dto.ownerapi.ReadOwnerResponse;
import co.drytools.backend.api.dto.ownerapi.UpdateOwnerRequest;
import co.drytools.backend.api.dto.ownerapi.UpdateOwnerResponse;
import co.drytools.backend.model.Owner;
import co.drytools.backend.model.User;
import co.drytools.backend.model.enumeration.PetType;
import co.drytools.backend.model.enumeration.UserRole;
import co.drytools.backend.model.id.OwnerId;
import co.drytools.backend.model.id.PetId;
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.repository.OwnerRepository;
import co.drytools.backend.repository.UserRepository;
import co.drytools.backend.repository.tuple.OwnerFindOwnerVetsTuple;
import co.drytools.backend.repository.tuple.OwnerFindOwnersForAddressTuple;
import co.drytools.backend.repository.tuple.OwnerMyPetsTuple;
import co.drytools.backend.repository.tuple.OwnerOwnersPetsTuple;
import co.drytools.backend.repository.tuple.OwnerOwnersWithPetsTuple;
import co.drytools.backend.service.SecurityService;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OwnerApi {
    private static final Logger LOG = LoggerFactory.getLogger(OwnerApi.class);

    @Inject private OwnerRepository ownerRepository;

    @Inject private UserRepository userRepository;

    @Inject private SecurityService securityService;

    public ReadOwnerResponse readOwner(ReadOwnerRequest dto) {
        LOG.trace("readOwner {}", dto.getId());
        // TODO check security constraints(id)

        final Owner model = ownerRepository.getOne(dto.getId());
        final OwnerId responseId = model.getId();
        final UserId responseUserId = model.getUserId();
        final String responseAddress = model.getAddress().orElse(null);
        final String responseCity = model.getCity().orElse(null);
        final String responseTelephone = model.getTelephone().orElse(null);
        return new ReadOwnerResponse(responseId, responseUserId, responseAddress, responseCity, responseTelephone);
    }

    public CreateOwnerResponse createOwner(CreateOwnerRequest dto) {
        LOG.trace("createOwner {}", dto.getUserId());
        // TODO check security constraints(userId)

        final User user = userRepository.getOne(dto.getUserId());
        final Optional<String> address = Optional.ofNullable(dto.getAddress());
        final Optional<String> city = Optional.ofNullable(dto.getCity());
        final Optional<String> telephone = Optional.ofNullable(dto.getTelephone());
        final Owner model = ownerRepository.create(null, Optional.empty(), Optional.empty(), Optional.empty());

        final OwnerId responseId = model.getId();
        final UserId responseUserId = model.getUserId();
        final String responseAddress = model.getAddress().orElse(null);
        final String responseCity = model.getCity().orElse(null);
        final String responseTelephone = model.getTelephone().orElse(null);
        return new CreateOwnerResponse(responseId, responseUserId, responseAddress, responseCity, responseTelephone);
    }

    public UpdateOwnerResponse updateOwner(UpdateOwnerRequest dto) {
        LOG.trace("updateOwner {} {}", dto.getId(), dto.getUserId());
        // TODO check security constraints(id, userId)

        final Owner model = ownerRepository.getOne(dto.getId());
        model.setUser(userRepository.getOne(dto.getUserId()));
        model.setAddress(Optional.ofNullable(dto.getAddress()));
        model.setCity(Optional.ofNullable(dto.getCity()));
        model.setTelephone(Optional.ofNullable(dto.getTelephone()));
        ownerRepository.update(model);

        final OwnerId responseId = model.getId();
        final UserId responseUserId = model.getUserId();
        final String responseAddress = model.getAddress().orElse(null);
        final String responseCity = model.getCity().orElse(null);
        final String responseTelephone = model.getTelephone().orElse(null);
        return new UpdateOwnerResponse(responseId, responseUserId, responseAddress, responseCity, responseTelephone);
    }

    public void deleteOwner(DeleteOwnerRequest dto) {
        LOG.trace("deleteOwner {}", dto.getId());
        // TODO check security constraints(id)

        ownerRepository.deleteById(dto.getId());
    }

    public List<AllOwnersResponse> allOwners(AllOwnersRequest dto) {
        LOG.trace("allOwners");
        // TODO check security constraints

        final List<Owner> models = ownerRepository.allOwnersPaged(dto.getParam());
        return models.stream()
                .map(
                        model -> {
                            final OwnerId responseId = model.getId();
                            final UserId responseUserId = model.getUserId();
                            final String responseAddress = model.getAddress().orElse(null);
                            final String responseCity = model.getCity().orElse(null);
                            final String responseTelephone = model.getTelephone().orElse(null);
                            return new AllOwnersResponse(responseId, responseUserId, responseAddress, responseCity, responseTelephone);
                        })
                .collect(Collectors.toList());
    }

    public PagedDTO<OwnersForAddressResponse> ownersForAddress(OwnersForAddressRequest dto) {
        LOG.trace("ownersForAddress");
        // TODO check security constraints

        final List<OwnerFindOwnersForAddressTuple> tuples =
                ownerRepository.findOwnersForAddress(Optional.ofNullable(dto.getAddress()), dto.getDrop(), dto.getTake());
        final Long totalCount = ownerRepository.countOwnersForAddress(Optional.ofNullable(dto.getAddress()));
        final List<OwnersForAddressResponse> dtos =
                tuples.stream()
                        .map(
                                tuple -> {
                                    final OwnerId responseId = tuple.getOwner().getId();
                                    final String responseUserEmail = tuple.getUser().getEmail();
                                    final String responseUserFirstName = tuple.getUser().getFirstName();
                                    final String responseUserLastName = tuple.getUser().getLastName();
                                    return new OwnersForAddressResponse(responseId, responseUserEmail, responseUserFirstName, responseUserLastName);
                                })
                        .collect(Collectors.toList());
        return new PagedDTO<>(dtos, totalCount);
    }

    public List<OwnersWithPetsResponse> ownersWithPets() {
        LOG.trace("ownersWithPets");
        // TODO check security constraints

        final List<OwnerOwnersWithPetsTuple> tuples = ownerRepository.ownersWithPets();
        return tuples.stream()
                .map(
                        tuple -> {
                            final String responseUserFirstName = tuple.getUser().getFirstName();
                            final String responseUserLastName = tuple.getUser().getLastName();
                            return new OwnersWithPetsResponse(responseUserFirstName, responseUserLastName);
                        })
                .collect(Collectors.toList());
    }

    public List<OwnersPetsResponse> ownersPets(OwnersPetsRequest dto) {
        LOG.trace("ownersPets {}", dto.getOwnerId());
        // TODO check security constraints(ownerId)

        final List<OwnerOwnersPetsTuple> tuples = ownerRepository.ownersPets(dto.getOwnerId());
        return tuples.stream()
                .map(
                        tuple -> {
                            final PetId responsePetId = tuple.getPet().getId();
                            final OwnerId responseId = tuple.getOwner().getId();
                            final String responsePetName = tuple.getPet().getName();
                            return new OwnersPetsResponse(responsePetId, responseId, responsePetName);
                        })
                .collect(Collectors.toList());
    }

    public List<MyPetsResponse> myPets() {
        LOG.trace("myPets");
        // TODO check security constraints

        final User principal = securityService.getPrincipal().get();

        final List<OwnerMyPetsTuple> tuples = ownerRepository.myPets(principal.getId());
        return tuples.stream()
                .map(
                        tuple -> {
                            final OwnerId responseId = tuple.getOwner().getId();
                            final UserId responseUserId = tuple.getOwner().getUserId();
                            final String responseAddress = tuple.getOwner().getAddress().orElse(null);
                            final String responseCity = tuple.getOwner().getCity().orElse(null);
                            final String responseTelephone = tuple.getOwner().getTelephone().orElse(null);
                            final PetId responsePetId = tuple.getPet().getId();
                            final OwnerId responsePetOwnerId = tuple.getPet().getOwnerId();
                            final String responsePetName = tuple.getPet().getName();
                            final ZonedDateTime responsePetBirthdate = tuple.getPet().getBirthdate();
                            final PetType responsePetPetType = tuple.getPet().getPetType();
                            final Boolean responsePetVaccinated = tuple.getPet().getVaccinated();
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
                            return new MyPetsResponse(
                                    responseId,
                                    responseUserId,
                                    responseAddress,
                                    responseCity,
                                    responseTelephone,
                                    responsePetId,
                                    responsePetOwnerId,
                                    responsePetName,
                                    responsePetBirthdate,
                                    responsePetPetType,
                                    responsePetVaccinated,
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
                .collect(Collectors.toList());
    }

    public List<OwnerVetsResponse> ownerVets() {
        LOG.trace("ownerVets");
        // TODO check security constraints

        final List<OwnerFindOwnerVetsTuple> tuples = ownerRepository.findOwnerVets();
        return tuples.stream()
                .map(
                        tuple -> {
                            final OwnerId responseId = tuple.getOwner().getId();
                            final UserId responseUserId = tuple.getOwner().getUserId();
                            final String responseAddress = tuple.getOwner().getAddress().orElse(null);
                            final String responseCity = tuple.getOwner().getCity().orElse(null);
                            final String responseTelephone = tuple.getOwner().getTelephone().orElse(null);
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
                            return new OwnerVetsResponse(
                                    responseId,
                                    responseUserId,
                                    responseAddress,
                                    responseCity,
                                    responseTelephone,
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
                .collect(Collectors.toList());
    }
}
