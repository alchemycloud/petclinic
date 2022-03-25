package co.aleksa.backend.api;

import co.aleksa.backend.api.dto.PagedDTO;
import co.aleksa.backend.api.dto.petapi.CreatePetRequest;
import co.aleksa.backend.api.dto.petapi.CreatePetResponse;
import co.aleksa.backend.api.dto.petapi.DeletePetRequest;
import co.aleksa.backend.api.dto.petapi.PetsByTypeRequest;
import co.aleksa.backend.api.dto.petapi.PetsByTypeResponse;
import co.aleksa.backend.api.dto.petapi.PetsRequest;
import co.aleksa.backend.api.dto.petapi.PetsResponse;
import co.aleksa.backend.api.dto.petapi.ReadPetRequest;
import co.aleksa.backend.api.dto.petapi.ReadPetResponse;
import co.aleksa.backend.api.dto.petapi.UpdatePetRequest;
import co.aleksa.backend.api.dto.petapi.UpdatePetResponse;
import co.aleksa.backend.model.Owner;
import co.aleksa.backend.model.Pet;
import co.aleksa.backend.model.enumeration.PetType;
import co.aleksa.backend.model.id.OwnerId;
import co.aleksa.backend.model.id.PetId;
import co.aleksa.backend.repository.OwnerRepository;
import co.aleksa.backend.repository.PetRepository;
import co.aleksa.backend.repository.tuple.PetFindPetsTuple;
import java.time.ZonedDateTime;
import java.util.List;
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
        final ZonedDateTime responseBirthday = model.getBirthday();
        final PetType responsePetType = model.getPetType();
        final Boolean responseVaccinated = model.getVaccinated();
        return new ReadPetResponse(responseId, responseOwnerId, responseName, responseBirthday, responsePetType, responseVaccinated);
    }

    public CreatePetResponse createPet(CreatePetRequest dto) {
        LOG.trace("createPet {}", dto.getOwnerId());
        // TODO check security constraints(ownerId)

        final Owner owner = ownerRepository.getOne(dto.getOwnerId());
        final String name = dto.getName();
        final ZonedDateTime birthday = dto.getBirthday();
        final PetType petType = dto.getPetType();
        final Boolean vaccinated = dto.getVaccinated();
        final Pet model = petRepository.create(null, null, null, null, null);

        final PetId responseId = model.getId();
        final OwnerId responseOwnerId = model.getOwnerId();
        final String responseName = model.getName();
        final ZonedDateTime responseBirthday = model.getBirthday();
        final PetType responsePetType = model.getPetType();
        final Boolean responseVaccinated = model.getVaccinated();
        return new CreatePetResponse(responseId, responseOwnerId, responseName, responseBirthday, responsePetType, responseVaccinated);
    }

    public UpdatePetResponse updatePet(UpdatePetRequest dto) {
        LOG.trace("updatePet {} {}", dto.getId(), dto.getOwnerId());
        // TODO check security constraints(id, ownerId)

        final Pet model = petRepository.getOne(dto.getId());
        model.setOwner(ownerRepository.getOne(dto.getOwnerId()));
        model.setName(dto.getName());
        model.setBirthday(dto.getBirthday());
        model.setPetType(dto.getPetType());
        model.setVaccinated(dto.getVaccinated());
        petRepository.update(model);

        final PetId responseId = model.getId();
        final OwnerId responseOwnerId = model.getOwnerId();
        final String responseName = model.getName();
        final ZonedDateTime responseBirthday = model.getBirthday();
        final PetType responsePetType = model.getPetType();
        final Boolean responseVaccinated = model.getVaccinated();
        return new UpdatePetResponse(responseId, responseOwnerId, responseName, responseBirthday, responsePetType, responseVaccinated);
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
                                    final String responseOwnerLastName = tuple.getUser().getLastName();
                                    return new PetsResponse(responseId, responseName, responsePetType, responseOwnerLastName);
                                })
                        .toList();
        return new PagedDTO<>(dtos, totalCount);
    }

    public List<PetsByTypeResponse> petsByType(PetsByTypeRequest dto) {
        LOG.trace("petsByType");
        // TODO check security constraints

        final List<Pet> models = petRepository.petsByType(dto.getPetType());
        return models.stream()
                .map(
                        model -> {
                            final PetId responseId = model.getId();
                            final String responseName = model.getName();
                            return new PetsByTypeResponse(responseId, responseName);
                        })
                .toList();
    }
}
