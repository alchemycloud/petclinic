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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class OwnerApiCaller {
    private static final Logger LOG = LoggerFactory.getLogger(OwnerApiCaller.class);

    @Lazy @Inject private AuditFacade auditFacade;

    @Inject private OwnerApiTransactionCaller ownerApiTransactionCaller;

    public ReadOwnersResponse readOwners(ReadOwnersRequest dto) {
        LOG.trace("readOwners {}", dto.getId());

        final ReadOwnersResponse result = ownerApiTransactionCaller.readOwners(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public CreateOwnersResponse createOwners(CreateOwnersRequest dto) {
        LOG.trace("createOwners {}", dto.getUserId());

        final CreateOwnersResponse result = ownerApiTransactionCaller.createOwners(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public UpdateOwnersResponse updateOwners(UpdateOwnersRequest dto) {
        LOG.trace("updateOwners {} {}", dto.getId(), dto.getUserId());

        final UpdateOwnersResponse result = ownerApiTransactionCaller.updateOwners(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public void deleteOwners(DeleteOwnersRequest dto) {
        LOG.trace("deleteOwners {}", dto.getId());

        ownerApiTransactionCaller.deleteOwners(dto);
        auditFacade.flushAfterTransaction();
    }

    public PagedDTO<AllOwnersResponse> allOwners(AllOwnersRequest dto) {
        LOG.trace("allOwners");

        final PagedDTO<AllOwnersResponse> result = ownerApiTransactionCaller.allOwners(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public PagedDTO<EnrichedOwnerDTO> forAddress(ForAddressRequest dto) {
        LOG.trace("forAddress");

        final PagedDTO<EnrichedOwnerDTO> result = ownerApiTransactionCaller.forAddress(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public List<EnrichedOwnerDTO> ownersWithPets() {
        LOG.trace("ownersWithPets");

        final List<EnrichedOwnerDTO> result = ownerApiTransactionCaller.ownersWithPets();
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
