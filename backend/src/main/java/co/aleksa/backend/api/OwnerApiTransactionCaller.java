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
import co.aleksa.backend.audit.AuditFacade;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerApiTransactionCaller {
    private static final Logger LOG = LoggerFactory.getLogger(OwnerApiTransactionCaller.class);

    @Inject private AuditFacade auditFacade;

    @Inject private OwnerApi ownerApi;

    @Transactional(readOnly = true)
    public ReadOwnersResponse readOwners(ReadOwnersRequest dto) {
        LOG.trace("readOwners {}", dto.getId());

        final ReadOwnersResponse result = ownerApi.readOwners(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public CreateOwnersResponse createOwners(CreateOwnersRequest dto) {
        LOG.trace("createOwners {}", dto.getUserId());

        final CreateOwnersResponse result = ownerApi.createOwners(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public UpdateOwnersResponse updateOwners(UpdateOwnersRequest dto) {
        LOG.trace("updateOwners {} {}", dto.getId(), dto.getUserId());

        final UpdateOwnersResponse result = ownerApi.updateOwners(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public void deleteOwners(DeleteOwnersRequest dto) {
        LOG.trace("deleteOwners {}", dto.getId());

        ownerApi.deleteOwners(dto);
        auditFacade.flushInTransaction();
    }

    @Transactional(readOnly = true)
    public PagedDTO<AllOwnersResponse> allOwners(AllOwnersRequest dto) {
        LOG.trace("allOwners");

        final PagedDTO<AllOwnersResponse> result = ownerApi.allOwners(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public PagedDTO<EnrichedOwnerDTO> forAddress(ForAddressRequest dto) {
        LOG.trace("forAddress");

        final PagedDTO<EnrichedOwnerDTO> result = ownerApi.forAddress(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public List<EnrichedOwnerDTO> ownersWithPets() {
        LOG.trace("ownersWithPets");

        final List<EnrichedOwnerDTO> result = ownerApi.ownersWithPets();
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public List<OwnersPetsResponse> ownersPets(OwnersPetsRequest dto) {
        LOG.trace("ownersPets {}", dto.getOwnerId());

        final List<OwnersPetsResponse> result = ownerApi.ownersPets(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public List<MyPetsResponse> myPets() {
        LOG.trace("myPets");

        final List<MyPetsResponse> result = ownerApi.myPets();
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public List<OwnerVetsResponse> ownerVets() {
        LOG.trace("ownerVets");

        final List<OwnerVetsResponse> result = ownerApi.ownerVets();
        auditFacade.flushInTransaction();
        return result;
    }
}
