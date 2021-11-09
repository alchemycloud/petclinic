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
import co.drytools.backend.audit.AuditFacade;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class PetApiCaller {
    private static final Logger LOG = LoggerFactory.getLogger(PetApiCaller.class);

    @Lazy @Inject private AuditFacade auditFacade;

    @Inject private PetApiTransactionCaller petApiTransactionCaller;

    public ReadPetResponse readPet(ReadPetRequest dto) {
        LOG.trace("readPet {}", dto.getId());

        final ReadPetResponse result = petApiTransactionCaller.readPet(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public CreatePetResponse createPet(CreatePetRequest dto) {
        LOG.trace("createPet {}", dto.getOwnerId());

        final CreatePetResponse result = petApiTransactionCaller.createPet(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public UpdatePetResponse updatePet(UpdatePetRequest dto) {
        LOG.trace("updatePet {} {}", dto.getId(), dto.getOwnerId());

        final UpdatePetResponse result = petApiTransactionCaller.updatePet(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public void deletePet(DeletePetRequest dto) {
        LOG.trace("deletePet {}", dto.getId());

        petApiTransactionCaller.deletePet(dto);
        auditFacade.flushAfterTransaction();
    }

    public PagedDTO<PetsResponse> pets(PetsRequest dto) {
        LOG.trace("pets");

        final PagedDTO<PetsResponse> result = petApiTransactionCaller.pets(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public List<FindPetbyTypeResponse> findPetbyType(FindPetbyTypeRequest dto) {
        LOG.trace("findPetbyType");

        final List<FindPetbyTypeResponse> result = petApiTransactionCaller.findPetbyType(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }
}
