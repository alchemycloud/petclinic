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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class VisitApiCaller {
    private static final Logger LOG = LoggerFactory.getLogger(VisitApiCaller.class);

    @Lazy @Inject private AuditFacade auditFacade;

    @Inject private VisitApiTransactionCaller visitApiTransactionCaller;

    public ReadVisitResponse readVisit(ReadVisitRequest dto) {
        LOG.trace("readVisit {}", dto.getId());

        final ReadVisitResponse result = visitApiTransactionCaller.readVisit(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public CreateVisitResponse createVisit(CreateVisitRequest dto) {
        LOG.trace("createVisit {} {}", dto.getVetId(), dto.getPetId());

        final CreateVisitResponse result = visitApiTransactionCaller.createVisit(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public UpdateVisitResponse updateVisit(UpdateVisitRequest dto) {
        LOG.trace("updateVisit {} {} {}", dto.getId(), dto.getVetId(), dto.getPetId());

        final UpdateVisitResponse result = visitApiTransactionCaller.updateVisit(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public void deleteVisit(DeleteVisitRequest dto) {
        LOG.trace("deleteVisit {}", dto.getId());

        visitApiTransactionCaller.deleteVisit(dto);
        auditFacade.flushAfterTransaction();
    }

    public PagedDTO<VisitDTO> visitsByVet(VisitsByVetRequest dto) {
        LOG.trace("visitsByVet {}", dto.getVetId());

        final PagedDTO<VisitDTO> result = visitApiTransactionCaller.visitsByVet(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public PagedDTO<VisitDTO> visitsByPet(VisitsByPetRequest dto) {
        LOG.trace("visitsByPet {}", dto.getPetId());

        final PagedDTO<VisitDTO> result = visitApiTransactionCaller.visitsByPet(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public PagedDTO<VisitDTO> scheduledVisits(ScheduledVisitsRequest dto) {
        LOG.trace("scheduledVisits");

        final PagedDTO<VisitDTO> result = visitApiTransactionCaller.scheduledVisits(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public PagedDTO<VisitDTO> visitsForOwner(VisitsForOwnerRequest dto) {
        LOG.trace("visitsForOwner");

        final PagedDTO<VisitDTO> result = visitApiTransactionCaller.visitsForOwner(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }
}
