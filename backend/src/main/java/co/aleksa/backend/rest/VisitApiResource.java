package co.aleksa.backend.rest;

import co.aleksa.backend.api.VisitApiCaller;
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
import co.aleksa.backend.model.id.PetId;
import co.aleksa.backend.model.id.VetId;
import co.aleksa.backend.model.id.VisitId;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/visits")
public class VisitApiResource {
    private static final Logger LOG = LoggerFactory.getLogger(VisitApiResource.class);

    @Inject private VisitApiCaller visitApiCaller;

    @GetMapping(value = "/read-visit", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<ReadVisitResponse> readVisit(@RequestParam("id") Long id) {
        LOG.debug("GET /visits/read-visit");

        final ReadVisitRequest request = new ReadVisitRequest(new VisitId(id));
        final ReadVisitResponse response = visitApiCaller.readVisit(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/create-visit", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<CreateVisitResponse> createVisit(@Valid @RequestBody CreateVisitRequest request) {
        LOG.debug("POST /visits/create-visit {}", request);

        final CreateVisitResponse response = visitApiCaller.createVisit(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/update-visit", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<UpdateVisitResponse> updateVisit(@Valid @RequestBody UpdateVisitRequest request) {
        LOG.debug("PUT /visits/update-visit {}", request);

        final UpdateVisitResponse response = visitApiCaller.updateVisit(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/delete-visit", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<Void> deleteVisit(@RequestParam("id") Long id) {
        LOG.debug("DELETE /visits/delete-visit");

        final DeleteVisitRequest request = new DeleteVisitRequest(new VisitId(id));
        visitApiCaller.deleteVisit(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/visits-by-vet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<PagedDTO<VisitDTO>> visitsByVet(
            @RequestParam("vetId") Long vetId, @RequestParam("drop") Integer drop, @RequestParam("take") Integer take) {
        LOG.debug("GET /visits/visits-by-vet");

        final VisitsByVetRequest request = new VisitsByVetRequest(new VetId(vetId), drop, take);
        final PagedDTO<VisitDTO> response = visitApiCaller.visitsByVet(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/visits-by-pet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<PagedDTO<VisitDTO>> visitsByPet(
            @RequestParam("petId") Long petId, @RequestParam("drop") Integer drop, @RequestParam("take") Integer take) {
        LOG.debug("GET /visits/visits-by-pet");

        final VisitsByPetRequest request = new VisitsByPetRequest(new PetId(petId), drop, take);
        final PagedDTO<VisitDTO> response = visitApiCaller.visitsByPet(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/scheduled-visits", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<PagedDTO<VisitDTO>> scheduledVisits(@RequestParam("drop") Integer drop, @RequestParam("take") Integer take) {
        LOG.debug("GET /visits/scheduled-visits");

        final ScheduledVisitsRequest request = new ScheduledVisitsRequest(drop, take);
        final PagedDTO<VisitDTO> response = visitApiCaller.scheduledVisits(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/visits-for-owner", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<PagedDTO<VisitDTO>> visitsForOwner(@RequestParam("drop") Integer drop, @RequestParam("take") Integer take) {
        LOG.debug("GET /visits/visits-for-owner");

        final VisitsForOwnerRequest request = new VisitsForOwnerRequest(drop, take);
        final PagedDTO<VisitDTO> response = visitApiCaller.visitsForOwner(request);
        return ResponseEntity.ok().body(response);
    }
}
