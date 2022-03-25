package co.aleksa.backend.rest;

import co.aleksa.backend.api.VetApiCaller;
import co.aleksa.backend.api.dto.vetapi.CreateVetRequest;
import co.aleksa.backend.api.dto.vetapi.CreateVetResponse;
import co.aleksa.backend.api.dto.vetapi.DeleteVetRequest;
import co.aleksa.backend.api.dto.vetapi.ReadVetRequest;
import co.aleksa.backend.api.dto.vetapi.ReadVetResponse;
import co.aleksa.backend.api.dto.vetapi.UpdateVetRequest;
import co.aleksa.backend.api.dto.vetapi.UpdateVetResponse;
import co.aleksa.backend.api.dto.vetapi.VetInfoRequest;
import co.aleksa.backend.api.dto.vetapi.VetWithSpecialtiesDTO;
import co.aleksa.backend.model.id.VetId;
import co.aleksa.backend.rest.dto.vetapi.RestCreateVetRequest;
import co.aleksa.backend.rest.dto.vetapi.RestUpdateVetRequest;
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
@RequestMapping("/api/vets")
public class VetApiResource {
    private static final Logger LOG = LoggerFactory.getLogger(VetApiResource.class);

    @Inject private VetApiCaller vetApiCaller;

    @GetMapping(value = "/read-vet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ReadVetResponse> readVet(@RequestParam("id") Long id) {
        LOG.debug("GET /vets/read-vet");

        final ReadVetRequest request = new ReadVetRequest(new VetId(id));
        final ReadVetResponse response = vetApiCaller.readVet(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/create-vet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CreateVetResponse> createVet(@Valid @RequestBody RestCreateVetRequest restRequest) throws IOException {
        LOG.trace("POST /vets/create-vet {}", restRequest);

        final CreateVetRequest request = new CreateVetRequest(restRequest.getUserId(), null, restRequest.getUserId());
        final CreateVetResponse response = vetApiCaller.createVet(request, restRequest.getImage());
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/update-vet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UpdateVetResponse> updateVet(@Valid @RequestBody RestUpdateVetRequest restRequest) throws IOException {
        LOG.trace("PUT /vets/update-vet {}", restRequest);

        final UpdateVetRequest request = new UpdateVetRequest(restRequest.getId(), restRequest.getUserId(), null, restRequest.getUserId());
        final UpdateVetResponse response = vetApiCaller.updateVet(request, restRequest.getImage());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/delete-vet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteVet(@RequestParam("id") Long id) {
        LOG.debug("DELETE /vets/delete-vet");

        final DeleteVetRequest request = new DeleteVetRequest(new VetId(id));
        vetApiCaller.deleteVet(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/vets-with-specialties", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<VetWithSpecialtiesDTO>> vetsWithSpecialties() {
        LOG.debug("GET /vets/vets-with-specialties");

        final List<VetWithSpecialtiesDTO> response = vetApiCaller.vetsWithSpecialties();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/vet/info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<VetWithSpecialtiesDTO>> vetInfo(@PathVariable Long id) {
        LOG.debug("GET /vets/vet/info/{}", id);

        final VetInfoRequest request = new VetInfoRequest(new VetId(id));
        final List<VetWithSpecialtiesDTO> response = vetApiCaller.vetInfo(request);
        return ResponseEntity.ok().body(response);
    }
}
