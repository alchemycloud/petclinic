package co.drytools.backend.rest;

import co.drytools.backend.api.VisitApiCaller;
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
import co.drytools.backend.model.id.UserId;
import co.drytools.backend.model.id.VisitId;
import co.drytools.backend.rest.dto.visitapi.RestUpdateVisitRequest;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/visit")
public class VisitApiResource {
    private static final Logger LOG = LoggerFactory.getLogger(VisitApiResource.class);

    @Inject private VisitApiCaller visitApiCaller;

    @GetMapping(value = "/visit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<ReadVisitResponse> readVisit(@PathVariable Long id) {
        LOG.debug("GET /visit/visit/{}", id);

        final ReadVisitRequest request = new ReadVisitRequest(new VisitId(id));
        final ReadVisitResponse response = visitApiCaller.readVisit(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/visit", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<CreateVisitResponse> createVisit(@Valid @RequestBody CreateVisitRequest request) {
        LOG.debug("POST /visit/visit {}", request);

        final CreateVisitResponse response = visitApiCaller.createVisit(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/visit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<UpdateVisitResponse> updateVisit(@PathVariable Long id, @Valid @RequestBody RestUpdateVisitRequest restRequest) {
        LOG.trace("PUT /visit/visit/{} {}", id, restRequest);

        final UpdateVisitRequest request =
                new UpdateVisitRequest(
                        new VisitId(id),
                        restRequest.getVetId(),
                        restRequest.getPetId(),
                        restRequest.getVisitNumber(),
                        restRequest.getTimestamp(),
                        restRequest.getPetWeight(),
                        restRequest.getDescription(),
                        restRequest.getScheduled());
        final UpdateVisitResponse response = visitApiCaller.updateVisit(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/visit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        LOG.debug("DELETE /visit/visit/{}", id);

        final DeleteVisitRequest request = new DeleteVisitRequest(new VisitId(id));
        visitApiCaller.deleteVisit(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/vet-visits", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<PagedDTO<VetVisitsResponse>> vetVisits(
            @RequestParam("userId") Long userId, @RequestParam("drop") Integer drop, @RequestParam("take") Integer take) {
        LOG.debug("GET /visit/vet-visits");

        final VetVisitsRequest request = new VetVisitsRequest(new UserId(userId), drop, take);
        final PagedDTO<VetVisitsResponse> response = visitApiCaller.vetVisits(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/scheduled", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<List<ScheduledVisitsResponse>> scheduledVisits() {
        LOG.debug("GET /visit/scheduled");

        final List<ScheduledVisitsResponse> response = visitApiCaller.scheduledVisits();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/my-visits", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<List<MyVisitsResponse>> myVisits(@RequestParam("userIdId") Long userIdId) {
        LOG.debug("GET /visit/my-visits");

        final MyVisitsRequest request = new MyVisitsRequest(new UserId(userIdId));
        final List<MyVisitsResponse> response = visitApiCaller.myVisits(request);
        return ResponseEntity.ok().body(response);
    }
}
