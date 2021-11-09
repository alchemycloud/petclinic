package co.drytools.backend.rest;

import co.drytools.backend.api.VetApiCaller;
import co.drytools.backend.api.dto.vetapi.CreateVetRequest;
import co.drytools.backend.api.dto.vetapi.CreateVetResponse;
import co.drytools.backend.api.dto.vetapi.DeleteVetRequest;
import co.drytools.backend.api.dto.vetapi.ReadVetRequest;
import co.drytools.backend.api.dto.vetapi.ReadVetResponse;
import co.drytools.backend.api.dto.vetapi.UpdateVetRequest;
import co.drytools.backend.api.dto.vetapi.UpdateVetResponse;
import co.drytools.backend.api.dto.vetapi.VetDTO;
import co.drytools.backend.api.dto.vetapi.VetInfoRequest;
import co.drytools.backend.api.dto.vetapi.VetWithSpecialtiesDTO;
import co.drytools.backend.model.id.VetId;
import co.drytools.backend.rest.dto.vetapi.RestCreateVetRequest;
import co.drytools.backend.rest.dto.vetapi.RestUpdateVetRequest;
import java.io.IOException;
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
@RequestMapping("/api/")
public class VetApiResource {
    private static final Logger LOG = LoggerFactory.getLogger(VetApiResource.class);

    @Inject private VetApiCaller vetApiCaller;

    @GetMapping(value = "/vet/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ReadVetResponse> readVet(@PathVariable Long id) {
        LOG.debug("GET /vet/{}", id);

        final ReadVetRequest request = new ReadVetRequest(new VetId(id));
        final ReadVetResponse response = vetApiCaller.readVet(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/vet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CreateVetResponse> createVet(@Valid @RequestBody RestCreateVetRequest restRequest) throws IOException {
        LOG.trace("POST /vet {}", restRequest);

        final CreateVetRequest request = new CreateVetRequest(restRequest.getUserId(), null);
        final CreateVetResponse response = vetApiCaller.createVet(request, restRequest.getImage());
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/vet/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UpdateVetResponse> updateVet(@PathVariable Long id, @Valid @RequestBody RestUpdateVetRequest restRequest) throws IOException {
        LOG.trace("PUT /vet/{} {}", id, restRequest);

        final UpdateVetRequest request = new UpdateVetRequest(new VetId(id), restRequest.getUserId(), null);
        final UpdateVetResponse response = vetApiCaller.updateVet(request, restRequest.getImage());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/vet/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteVet(@PathVariable Long id) {
        LOG.debug("DELETE /vet/{}", id);

        final DeleteVetRequest request = new DeleteVetRequest(new VetId(id));
        vetApiCaller.deleteVet(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/vets", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<VetWithSpecialtiesDTO>> vetsWithSpecialties(
            @RequestParam("id") Long id, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        LOG.debug("GET /vets");

        final VetDTO request = new VetDTO(new VetId(id), firstName, lastName);
        final List<VetWithSpecialtiesDTO> response = vetApiCaller.vetsWithSpecialties(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/vet/info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<VetWithSpecialtiesDTO> vetInfo(@PathVariable Long id) {
        LOG.debug("GET /vet/info/{}", id);

        final VetInfoRequest request = new VetInfoRequest(new VetId(id));
        final VetWithSpecialtiesDTO response = vetApiCaller.vetInfo(request);
        return ResponseEntity.ok().body(response);
    }
}
