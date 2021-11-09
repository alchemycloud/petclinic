package co.drytools.backend.api;

import co.drytools.backend.api.dto.PagedDTO;
import co.drytools.backend.api.dto.visitapi.CreateVisitRequest;
import co.drytools.backend.api.dto.visitapi.CreateVisitResponse;
import co.drytools.backend.api.dto.visitapi.DeleteVisitRequest;
import co.drytools.backend.api.dto.visitapi.MyVisitsRequest;
import co.drytools.backend.api.dto.visitapi.MyVisitsResponse;
import co.drytools.backend.api.dto.visitapi.ReadVisitRequest;
import co.drytools.backend.api.dto.visitapi.ReadVisitResponse;
import co.drytools.backend.api.dto.visitapi.ScheduledVisitsResponse;
import co.drytools.backend.api.dto.visitapi.UpdateVisitRequest;
import co.drytools.backend.api.dto.visitapi.UpdateVisitResponse;
import co.drytools.backend.api.dto.visitapi.VetVisitsRequest;
import co.drytools.backend.api.dto.visitapi.VetVisitsResponse;
import co.drytools.backend.audit.AuditFacade;
import java.util.List;
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

    public PagedDTO<VetVisitsResponse> vetVisits(VetVisitsRequest dto) {
        LOG.trace("vetVisits {}", dto.getUserId());

        final PagedDTO<VetVisitsResponse> result = visitApiTransactionCaller.vetVisits(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public List<ScheduledVisitsResponse> scheduledVisits() {
        LOG.trace("scheduledVisits");

        final List<ScheduledVisitsResponse> result = visitApiTransactionCaller.scheduledVisits();
        auditFacade.flushAfterTransaction();
        return result;
    }

    public List<MyVisitsResponse> myVisits(MyVisitsRequest dto) {
        LOG.trace("myVisits {}", dto.getUserIdId());

        final List<MyVisitsResponse> result = visitApiTransactionCaller.myVisits(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }
}
