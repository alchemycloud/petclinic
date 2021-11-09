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
import co.drytools.backend.audit.AuditFacade;
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
    public ReadOwnerResponse readOwner(ReadOwnerRequest dto) {
        LOG.trace("readOwner {}", dto.getId());

        final ReadOwnerResponse result = ownerApi.readOwner(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public CreateOwnerResponse createOwner(CreateOwnerRequest dto) {
        LOG.trace("createOwner {}", dto.getUserId());

        final CreateOwnerResponse result = ownerApi.createOwner(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public UpdateOwnerResponse updateOwner(UpdateOwnerRequest dto) {
        LOG.trace("updateOwner {} {}", dto.getId(), dto.getUserId());

        final UpdateOwnerResponse result = ownerApi.updateOwner(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public void deleteOwner(DeleteOwnerRequest dto) {
        LOG.trace("deleteOwner {}", dto.getId());

        ownerApi.deleteOwner(dto);
        auditFacade.flushInTransaction();
    }

    @Transactional(readOnly = true)
    public List<AllOwnersResponse> allOwners(AllOwnersRequest dto) {
        LOG.trace("allOwners");

        final List<AllOwnersResponse> result = ownerApi.allOwners(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public PagedDTO<OwnersForAddressResponse> ownersForAddress(OwnersForAddressRequest dto) {
        LOG.trace("ownersForAddress");

        final PagedDTO<OwnersForAddressResponse> result = ownerApi.ownersForAddress(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public List<OwnersWithPetsResponse> ownersWithPets() {
        LOG.trace("ownersWithPets");

        final List<OwnersWithPetsResponse> result = ownerApi.ownersWithPets();
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
