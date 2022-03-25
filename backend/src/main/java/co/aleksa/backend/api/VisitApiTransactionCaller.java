package co.aleksa.backend.api;

import co.aleksa.backend.api.dto.PagedDTO;
import co.aleksa.backend.api.dto.visitapi.CreateVisitRequest;
import co.aleksa.backend.api.dto.visitapi.CreateVisitResponse;
import co.aleksa.backend.api.dto.visitapi.DeleteVisitRequest;
import co.aleksa.backend.api.dto.visitapi.ReadVisitRequest;
import co.aleksa.backend.api.dto.visitapi.ReadVisitResponse;
import co.aleksa.backend.api.dto.visitapi.ScheduledVisitsRequest;
import co.aleksa.backend.api.dto.visitapi.UpdateVisitRequest;
import co.aleksa.backend.api.dto.visitapi.UpdateVisitResponse;
import co.aleksa.backend.api.dto.visitapi.VisitDTO;
import co.aleksa.backend.api.dto.visitapi.VisitsByPetRequest;
import co.aleksa.backend.api.dto.visitapi.VisitsByVetRequest;
import co.aleksa.backend.api.dto.visitapi.VisitsForOwnerRequest;
import co.aleksa.backend.audit.AuditFacade;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitApiTransactionCaller {
    private static final Logger LOG = LoggerFactory.getLogger(VisitApiTransactionCaller.class);

    @Inject private AuditFacade auditFacade;

    @Inject private VisitApi visitApi;

    @Transactional(readOnly = true)
    public ReadVisitResponse readVisit(ReadVisitRequest dto) {
        LOG.trace("readVisit {}", dto.getId());

        final ReadVisitResponse result = visitApi.readVisit(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public CreateVisitResponse createVisit(CreateVisitRequest dto) {
        LOG.trace("createVisit {} {}", dto.getVetId(), dto.getPetId());

        final CreateVisitResponse result = visitApi.createVisit(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public UpdateVisitResponse updateVisit(UpdateVisitRequest dto) {
        LOG.trace("updateVisit {} {} {}", dto.getId(), dto.getVetId(), dto.getPetId());

        final UpdateVisitResponse result = visitApi.updateVisit(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public void deleteVisit(DeleteVisitRequest dto) {
        LOG.trace("deleteVisit {}", dto.getId());

        visitApi.deleteVisit(dto);
        auditFacade.flushInTransaction();
    }

    @Transactional(readOnly = true)
    public PagedDTO<VisitDTO> visitsByVet(VisitsByVetRequest dto) {
        LOG.trace("visitsByVet {}", dto.getVetId());

        final PagedDTO<VisitDTO> result = visitApi.visitsByVet(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public PagedDTO<VisitDTO> visitsByPet(VisitsByPetRequest dto) {
        LOG.trace("visitsByPet {}", dto.getPetId());

        final PagedDTO<VisitDTO> result = visitApi.visitsByPet(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public PagedDTO<VisitDTO> scheduledVisits(ScheduledVisitsRequest dto) {
        LOG.trace("scheduledVisits");

        final PagedDTO<VisitDTO> result = visitApi.scheduledVisits(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public PagedDTO<VisitDTO> visitsForOwner(VisitsForOwnerRequest dto) {
        LOG.trace("visitsForOwner");

        final PagedDTO<VisitDTO> result = visitApi.visitsForOwner(dto);
        auditFacade.flushInTransaction();
        return result;
    }
}
