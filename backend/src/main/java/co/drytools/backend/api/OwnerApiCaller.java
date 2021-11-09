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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class OwnerApiCaller {
    private static final Logger LOG = LoggerFactory.getLogger(OwnerApiCaller.class);

    @Lazy @Inject private AuditFacade auditFacade;

    @Inject private OwnerApiTransactionCaller ownerApiTransactionCaller;

    public ReadOwnerResponse readOwner(ReadOwnerRequest dto) {
        LOG.trace("readOwner {}", dto.getId());

        final ReadOwnerResponse result = ownerApiTransactionCaller.readOwner(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public CreateOwnerResponse createOwner(CreateOwnerRequest dto) {
        LOG.trace("createOwner {}", dto.getUserId());

        final CreateOwnerResponse result = ownerApiTransactionCaller.createOwner(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public UpdateOwnerResponse updateOwner(UpdateOwnerRequest dto) {
        LOG.trace("updateOwner {} {}", dto.getId(), dto.getUserId());

        final UpdateOwnerResponse result = ownerApiTransactionCaller.updateOwner(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public void deleteOwner(DeleteOwnerRequest dto) {
        LOG.trace("deleteOwner {}", dto.getId());

        ownerApiTransactionCaller.deleteOwner(dto);
        auditFacade.flushAfterTransaction();
    }

    public List<AllOwnersResponse> allOwners(AllOwnersRequest dto) {
        LOG.trace("allOwners");

        final List<AllOwnersResponse> result = ownerApiTransactionCaller.allOwners(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public PagedDTO<OwnersForAddressResponse> ownersForAddress(OwnersForAddressRequest dto) {
        LOG.trace("ownersForAddress");

        final PagedDTO<OwnersForAddressResponse> result = ownerApiTransactionCaller.ownersForAddress(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public List<OwnersWithPetsResponse> ownersWithPets() {
        LOG.trace("ownersWithPets");

        final List<OwnersWithPetsResponse> result = ownerApiTransactionCaller.ownersWithPets();
        auditFacade.flushAfterTransaction();
        return result;
    }

    public List<OwnersPetsResponse> ownersPets(OwnersPetsRequest dto) {
        LOG.trace("ownersPets {}", dto.getOwnerId());

        final List<OwnersPetsResponse> result = ownerApiTransactionCaller.ownersPets(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public List<MyPetsResponse> myPets() {
        LOG.trace("myPets");

        final List<MyPetsResponse> result = ownerApiTransactionCaller.myPets();
        auditFacade.flushAfterTransaction();
        return result;
    }

    public List<OwnerVetsResponse> ownerVets() {
        LOG.trace("ownerVets");

        final List<OwnerVetsResponse> result = ownerApiTransactionCaller.ownerVets();
        auditFacade.flushAfterTransaction();
        return result;
    }
}
