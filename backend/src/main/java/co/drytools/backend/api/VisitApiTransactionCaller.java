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
    public PagedDTO<VetVisitsResponse> vetVisits(VetVisitsRequest dto) {
        LOG.trace("vetVisits {}", dto.getUserId());

        final PagedDTO<VetVisitsResponse> result = visitApi.vetVisits(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public List<ScheduledVisitsResponse> scheduledVisits() {
        LOG.trace("scheduledVisits");

        final List<ScheduledVisitsResponse> result = visitApi.scheduledVisits();
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public List<MyVisitsResponse> myVisits(MyVisitsRequest dto) {
        LOG.trace("myVisits {}", dto.getUserIdId());

        final List<MyVisitsResponse> result = visitApi.myVisits(dto);
        auditFacade.flushInTransaction();
        return result;
    }
}
