package co.aleksa.backend.api;

import co.aleksa.backend.api.dto.PagedDTO;
import co.aleksa.backend.api.dto.ownerapi.AllOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.AllOwnersResponse;
import co.aleksa.backend.api.dto.ownerapi.CreateOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.CreateOwnersResponse;
import co.aleksa.backend.api.dto.ownerapi.DeleteOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.EnrichedOwnerDTO;
import co.aleksa.backend.api.dto.ownerapi.ForAddressRequest;
import co.aleksa.backend.api.dto.ownerapi.MyPetsResponse;
import co.aleksa.backend.api.dto.ownerapi.OwnerVetsResponse;
import co.aleksa.backend.api.dto.ownerapi.OwnersPetsRequest;
import co.aleksa.backend.api.dto.ownerapi.OwnersPetsResponse;
import co.aleksa.backend.api.dto.ownerapi.ReadOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.ReadOwnersResponse;
import co.aleksa.backend.api.dto.ownerapi.UpdateOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.UpdateOwnersResponse;
import co.aleksa.backend.model.Owner;
import co.aleksa.backend.model.User;
import co.aleksa.backend.model.enumeration.PetType;
import co.aleksa.backend.model.enumeration.UserRole;
import co.aleksa.backend.model.id.OwnerId;
import co.aleksa.backend.model.id.PetId;
import co.aleksa.backend.model.id.UserId;
import co.aleksa.backend.repository.OwnerRepository;
import co.aleksa.backend.repository.UserRepository;
import co.aleksa.backend.repository.tuple.OwnerFindForAddressTuple;
import co.aleksa.backend.repository.tuple.OwnerMyPetsTuple;
import co.aleksa.backend.repository.tuple.OwnerOwnerVetsTuple;
import co.aleksa.backend.repository.tuple.OwnerOwnersPetsTuple;
import co.aleksa.backend.repository.tuple.OwnerOwnersWithPetsTuple;
import co.aleksa.backend.service.SecurityService;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
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

    public ReadOwnersResponse readOwners(ReadOwnersRequest dto) {
        LOG.trace("readOwners {}", dto.getId());
        // TODO check security constraints(id)

        final Owner model = ownerRepository.getOne(dto.getId());
        final OwnerId responseId = model.getId();
        final UserId responseUserId = model.getUserId();
        final String responseAddress = model.getAddress().orElse(null);
        final String responseCity = model.getCity().orElse(null);
        final String responseTelephone = model.getTelephone().orElse(null);
        return new ReadOwnersResponse(responseId, responseUserId, responseAddress, responseCity, responseTelephone);
    }

    public CreateOwnersResponse createOwners(CreateOwnersRequest dto) {
        LOG.trace("createOwners {}", dto.getUserId());
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
        return new CreateOwnersResponse(responseId, responseUserId, responseAddress, responseCity, responseTelephone);
    }

    public UpdateOwnersResponse updateOwners(UpdateOwnersRequest dto) {
        LOG.trace("updateOwners {} {}", dto.getId(), dto.getUserId());
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
        return new UpdateOwnersResponse(responseId, responseUserId, responseAddress, responseCity, responseTelephone);
    }

    public void deleteOwners(DeleteOwnersRequest dto) {
        LOG.trace("deleteOwners {}", dto.getId());
        // TODO check security constraints(id)

        ownerRepository.deleteById(dto.getId());
    }

    public PagedDTO<AllOwnersResponse> allOwners(AllOwnersRequest dto) {
        LOG.trace("allOwners");
        // TODO check security constraints

        final List<Owner> models = ownerRepository.findAllOwners(dto.getDrop(), dto.getTake());
        final Long totalCount = ownerRepository.countAllOwners();
        final List<AllOwnersResponse> dtos =
                models.stream()
                        .map(
                                model -> {
                                    final OwnerId responseId = model.getId();
                                    final UserId responseUserId = model.getUserId();
                                    final String responseAddress = model.getAddress().orElse(null);
                                    final String responseCity = model.getCity().orElse(null);
                                    final String responseTelephone = model.getTelephone().orElse(null);
                                    return new AllOwnersResponse(responseId, responseUserId, responseAddress, responseCity, responseTelephone);
                                })
                        .toList();
        return new PagedDTO<>(dtos, totalCount);
    }

    public PagedDTO<EnrichedOwnerDTO> forAddress(ForAddressRequest dto) {
        LOG.trace("forAddress");
        // TODO check security constraints

        final List<OwnerFindForAddressTuple> tuples = ownerRepository.findForAddress(Optional.ofNullable(dto.getAddress()), dto.getDrop(), dto.getTake());
        final Long totalCount = ownerRepository.countForAddress(Optional.ofNullable(dto.getAddress()));
        final List<EnrichedOwnerDTO> dtos =
                tuples.stream()
                        .map(
                                tuple -> {
                                    final OwnerId responseId = tuple.getOwner().getId();
                                    final String responseEmail = tuple.getUser().getEmail();
                                    final String responseFirstName = tuple.getUser().getFirstName();
                                    final String responseLastName = tuple.getUser().getLastName();
                                    return new EnrichedOwnerDTO(responseId, responseEmail, responseFirstName, responseLastName);
                                })
                        .toList();
        return new PagedDTO<>(dtos, totalCount);
    }

    public List<EnrichedOwnerDTO> ownersWithPets() {
        LOG.trace("ownersWithPets");
        // TODO check security constraints

        final List<OwnerOwnersWithPetsTuple> tuples = ownerRepository.ownersWithPets();
        return tuples.stream()
                .map(
                        tuple -> {
                            final OwnerId responseId = tuple.getOwner().getId();
                            final String responseEmail = tuple.getUser().getEmail();
                            final String responseFirstName = tuple.getUser().getFirstName();
                            final String responseLastName = tuple.getUser().getLastName();
                            return new EnrichedOwnerDTO(responseId, responseEmail, responseFirstName, responseLastName);
                        })
                .toList();
    }

    public List<OwnersPetsResponse> ownersPets(OwnersPetsRequest dto) {
        LOG.trace("ownersPets {}", dto.getOwnerId());
        // TODO check security constraints(ownerId)

        final List<OwnerOwnersPetsTuple> tuples = ownerRepository.ownersPets(dto.getOwnerId());
        return tuples.stream()
                .map(
                        tuple -> {
                            final PetId responseId = tuple.getPet().getId();
                            final OwnerId responseOwnerId = tuple.getOwner().getId();
                            final String responseName = tuple.getPet().getName();
                            return new OwnersPetsResponse(responseId, responseOwnerId, responseName);
                        })
                .toList();
    }

    public List<MyPetsResponse> myPets() {
        LOG.trace("myPets");
        // TODO check security constraints

        final User principal = securityService.getPrincipal().get();

        final List<OwnerMyPetsTuple> tuples = ownerRepository.myPets(principal.getId());
        return tuples.stream()
                .map(
                        tuple -> {
                            final PetId responseId = tuple.getPet().getId();
                            final String responseName = tuple.getPet().getName();
                            final ZonedDateTime responseBirthday = tuple.getPet().getBirthday();
                            final PetType responsePetType = tuple.getPet().getPetType();
                            final Boolean responseVaccinated = tuple.getPet().getVaccinated();
                            return new MyPetsResponse(responseId, responseName, responseBirthday, responsePetType, responseVaccinated);
                        })
                .toList();
    }

    public List<OwnerVetsResponse> ownerVets() {
        LOG.trace("ownerVets");
        // TODO check security constraints

        final List<OwnerOwnerVetsTuple> tuples = ownerRepository.ownerVets();
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
                            final String responseUserEmail = tuple.getUser().getEmail();
                            final ZonedDateTime responseUserBirthday = tuple.getUser().getBirthday();
                            final Boolean responseUserActive = tuple.getUser().getActive();
                            final UserRole responseUserRole = tuple.getUser().getRole();
                            return new OwnerVetsResponse(
                                    responseId,
                                    responseUserId,
                                    responseAddress,
                                    responseCity,
                                    responseTelephone,
                                    responseUserFirstName,
                                    responseUserLastName,
                                    responseUserEmail,
                                    responseUserBirthday,
                                    responseUserActive,
                                    responseUserRole);
                        })
                .toList();
    }
}
