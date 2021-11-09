package co.drytools.backend.rest;

import co.drytools.backend.api.PetApiCaller;
import co.drytools.backend.api.dto.PagedDTO;
import co.drytools.backend.api.dto.petapi.CreatePetRequest;
import co.drytools.backend.api.dto.petapi.CreatePetResponse;
import co.drytools.backend.api.dto.petapi.DeletePetRequest;
import co.drytools.backend.api.dto.petapi.FindPetbyTypeRequest;
import co.drytools.backend.api.dto.petapi.FindPetbyTypeResponse;
import co.drytools.backend.api.dto.petapi.PetsRequest;
import co.drytools.backend.api.dto.petapi.PetsResponse;
import co.drytools.backend.api.dto.petapi.ReadPetRequest;
import co.drytools.backend.api.dto.petapi.ReadPetResponse;
import co.drytools.backend.api.dto.petapi.UpdatePetRequest;
import co.drytools.backend.api.dto.petapi.UpdatePetResponse;
import co.drytools.backend.model.enumeration.PetType;
import co.drytools.backend.model.id.PetId;
import co.drytools.backend.rest.dto.petapi.RestUpdatePetRequest;
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
public class PetApiResource {
    private static final Logger LOG = LoggerFactory.getLogger(PetApiResource.class);

    @Inject private PetApiCaller petApiCaller;

    @GetMapping(value = "/pet/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<ReadPetResponse> readPet(@PathVariable Long id) {
        LOG.debug("GET /pet/{}", id);

        final ReadPetRequest request = new ReadPetRequest(new PetId(id));
        final ReadPetResponse response = petApiCaller.readPet(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/pet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<CreatePetResponse> createPet(@Valid @RequestBody CreatePetRequest request) {
        LOG.debug("POST /pet {}", request);

        final CreatePetResponse response = petApiCaller.createPet(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/pet/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<UpdatePetResponse> updatePet(@PathVariable Long id, @Valid @RequestBody RestUpdatePetRequest restRequest) {
        LOG.trace("PUT /pet/{} {}", id, restRequest);

        final UpdatePetRequest request =
                new UpdatePetRequest(
                        new PetId(id),
                        restRequest.getOwnerId(),
                        restRequest.getName(),
                        restRequest.getBirthdate(),
                        restRequest.getPetType(),
                        restRequest.getVaccinated());
        final UpdatePetResponse response = petApiCaller.updatePet(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/pet/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        LOG.debug("DELETE /pet/{}", id);

        final DeletePetRequest request = new DeletePetRequest(new PetId(id));
        petApiCaller.deletePet(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/pets", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<PagedDTO<PetsResponse>> pets(@RequestParam("drop") Integer drop, @RequestParam("take") Integer take) {
        LOG.debug("GET /pets");

        final PetsRequest request = new PetsRequest(drop, take);
        final PagedDTO<PetsResponse> response = petApiCaller.pets(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/find-petby-type", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<List<FindPetbyTypeResponse>> findPetbyType(@RequestParam("petType") PetType petType) {
        LOG.debug("GET /find-petby-type");

        final FindPetbyTypeRequest request = new FindPetbyTypeRequest(petType);
        final List<FindPetbyTypeResponse> response = petApiCaller.findPetbyType(request);
        return ResponseEntity.ok().body(response);
    }
}
