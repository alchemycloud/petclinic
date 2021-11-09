package co.drytools.backend.api;

import co.drytools.backend.api.dto.PagedDTO;
import co.drytools.backend.api.dto.petapi.CreatePetRequest;
import co.drytools.backend.api.dto.petapi.CreatePetResponse;
import co.drytools.backend.api.dto.petapi.DeletePetRequest;
import co.drytools.backend.api.dto.petapi.FindPetbyTypeRequest;
import co.drytools.backend.api.dto.petapi.FindPetbyTypeResponse;
import co.drytools.backend.api.dto.petapi.PetsRequest;
import co.drytools.backend.api.dto.petapi.PetsResponse;
import co.drytools.backend.api.dto.petapi.ReadPetRequest;
import co.drytools.backend.api.dto.petapi.ReadPetResponse;
import co.drytools.backend.api.dto.petapi.UpdatePetRequest;
import co.drytools.backend.api.dto.petapi.UpdatePetResponse;
import co.drytools.backend.model.Owner;
import co.drytools.backend.model.Pet;
import co.drytools.backend.model.enumeration.PetType;
import co.drytools.backend.model.id.OwnerId;
import co.drytools.backend.model.id.PetId;
import co.drytools.backend.repository.OwnerRepository;
import co.drytools.backend.repository.PetRepository;
import co.drytools.backend.repository.tuple.PetFindPetsTuple;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PetApi {
    private static final Logger LOG = LoggerFactory.getLogger(PetApi.class);

    @Inject private PetRepository petRepository;

    @Inject private OwnerRepository ownerRepository;

    public ReadPetResponse readPet(ReadPetRequest dto) {
        LOG.trace("readPet {}", dto.getId());
        // TODO check security constraints(id)

        final Pet model = petRepository.getOne(dto.getId());
        final PetId responseId = model.getId();
        final OwnerId responseOwnerId = model.getOwnerId();
        final String responseName = model.getName();
        final ZonedDateTime responseBirthdate = model.getBirthdate();
        final PetType responsePetType = model.getPetType();
        final Boolean responseVaccinated = model.getVaccinated();
        return new ReadPetResponse(responseId, responseOwnerId, responseName, responseBirthdate, responsePetType, responseVaccinated);
    }

    public CreatePetResponse createPet(CreatePetRequest dto) {
        LOG.trace("createPet {}", dto.getOwnerId());
        // TODO check security constraints(ownerId)

        final Owner owner = ownerRepository.getOne(dto.getOwnerId());
        final String name = dto.getName();
        final ZonedDateTime birthdate = dto.getBirthdate();
        final PetType petType = dto.getPetType();
        final Boolean vaccinated = dto.getVaccinated();
        final Pet model = petRepository.create(null, null, null, null, null);

        final PetId responseId = model.getId();
        final OwnerId responseOwnerId = model.getOwnerId();
        final String responseName = model.getName();
        final ZonedDateTime responseBirthdate = model.getBirthdate();
        final PetType responsePetType = model.getPetType();
        final Boolean responseVaccinated = model.getVaccinated();
        return new CreatePetResponse(responseId, responseOwnerId, responseName, responseBirthdate, responsePetType, responseVaccinated);
    }

    public UpdatePetResponse updatePet(UpdatePetRequest dto) {
        LOG.trace("updatePet {} {}", dto.getId(), dto.getOwnerId());
        // TODO check security constraints(id, ownerId)

        final Pet model = petRepository.getOne(dto.getId());
        model.setOwner(ownerRepository.getOne(dto.getOwnerId()));
        model.setName(dto.getName());
        model.setBirthdate(dto.getBirthdate());
        model.setPetType(dto.getPetType());
        model.setVaccinated(dto.getVaccinated());
        petRepository.update(model);

        final PetId responseId = model.getId();
        final OwnerId responseOwnerId = model.getOwnerId();
        final String responseName = model.getName();
        final ZonedDateTime responseBirthdate = model.getBirthdate();
        final PetType responsePetType = model.getPetType();
        final Boolean responseVaccinated = model.getVaccinated();
        return new UpdatePetResponse(responseId, responseOwnerId, responseName, responseBirthdate, responsePetType, responseVaccinated);
    }

    public void deletePet(DeletePetRequest dto) {
        LOG.trace("deletePet {}", dto.getId());
        // TODO check security constraints(id)

        petRepository.deleteById(dto.getId());
    }

    public PagedDTO<PetsResponse> pets(PetsRequest dto) {
        LOG.trace("pets");
        // TODO check security constraints

        final List<PetFindPetsTuple> tuples = petRepository.findPets(dto.getDrop(), dto.getTake());
        final Long totalCount = petRepository.countPets();
        final List<PetsResponse> dtos =
                tuples.stream()
                        .map(
                                tuple -> {
                                    final PetId responseId = tuple.getPet().getId();
                                    final String responseName = tuple.getPet().getName();
                                    final PetType responsePetType = tuple.getPet().getPetType();
                                    final String responseUserLastName = tuple.getUser().getLastName();
                                    return new PetsResponse(responseId, responseName, responsePetType, responseUserLastName);
                                })
                        .collect(Collectors.toList());
        return new PagedDTO<>(dtos, totalCount);
    }

    public List<FindPetbyTypeResponse> findPetbyType(FindPetbyTypeRequest dto) {
        LOG.trace("findPetbyType");
        // TODO check security constraints

        final List<Pet> models = petRepository.findPetbyType(dto.getPetType());
        return models.stream()
                .map(
                        model -> {
                            final String responseName = model.getName();
                            final PetType responsePetType = model.getPetType();
                            return new FindPetbyTypeResponse(responseName, responsePetType);
                        })
                .collect(Collectors.toList());
    }
}
