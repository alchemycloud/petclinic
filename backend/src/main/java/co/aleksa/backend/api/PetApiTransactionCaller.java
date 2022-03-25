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
import co.aleksa.backend.audit.AuditFacade;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetApiTransactionCaller {
    private static final Logger LOG = LoggerFactory.getLogger(PetApiTransactionCaller.class);

    @Inject private AuditFacade auditFacade;

    @Inject private PetApi petApi;

    @Transactional(readOnly = true)
    public ReadPetResponse readPet(ReadPetRequest dto) {
        LOG.trace("readPet {}", dto.getId());

        final ReadPetResponse result = petApi.readPet(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public CreatePetResponse createPet(CreatePetRequest dto) {
        LOG.trace("createPet {}", dto.getOwnerId());

        final CreatePetResponse result = petApi.createPet(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public UpdatePetResponse updatePet(UpdatePetRequest dto) {
        LOG.trace("updatePet {} {}", dto.getId(), dto.getOwnerId());

        final UpdatePetResponse result = petApi.updatePet(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public void deletePet(DeletePetRequest dto) {
        LOG.trace("deletePet {}", dto.getId());

        petApi.deletePet(dto);
        auditFacade.flushInTransaction();
    }

    @Transactional(readOnly = true)
    public PagedDTO<PetsResponse> pets(PetsRequest dto) {
        LOG.trace("pets");

        final PagedDTO<PetsResponse> result = petApi.pets(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public List<PetsByTypeResponse> petsByType(PetsByTypeRequest dto) {
        LOG.trace("petsByType");

        final List<PetsByTypeResponse> result = petApi.petsByType(dto);
        auditFacade.flushInTransaction();
        return result;
    }
}
