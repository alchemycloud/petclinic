package co.aleksa.backend.rest;

import co.aleksa.backend.api.PetApiCaller;
import co.aleksa.backend.api.dto.PagedDTO;
import co.aleksa.backend.api.dto.petapi.CreatePetRequest;
import co.aleksa.backend.api.dto.petapi.CreatePetResponse;
import co.aleksa.backend.api.dto.petapi.DeletePetRequest;
import co.aleksa.backend.api.dto.petapi.PetsByTypeRequest;
import co.aleksa.backend.api.dto.petapi.PetsByTypeResponse;
import co.aleksa.backend.api.dto.petapi.PetsRequest;
import co.aleksa.backend.api.dto.petapi.PetsResponse;
import co.aleksa.backend.api.dto.petapi.ReadPetRequest;
import co.aleksa.backend.api.dto.petapi.ReadPetResponse;
import co.aleksa.backend.api.dto.petapi.UpdatePetRequest;
import co.aleksa.backend.api.dto.petapi.UpdatePetResponse;
import co.aleksa.backend.model.enumeration.PetType;
import co.aleksa.backend.model.id.PetId;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pets")
public class PetApiResource {
    private static final Logger LOG = LoggerFactory.getLogger(PetApiResource.class);

    @Inject private PetApiCaller petApiCaller;

    @GetMapping(value = "/read-pet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<ReadPetResponse> readPet(@RequestParam("id") Long id) {
        LOG.debug("GET /pets/read-pet");

        final ReadPetRequest request = new ReadPetRequest(new PetId(id));
        final ReadPetResponse response = petApiCaller.readPet(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/create-pet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<CreatePetResponse> createPet(@Valid @RequestBody CreatePetRequest request) {
        LOG.debug("POST /pets/create-pet {}", request);

        final CreatePetResponse response = petApiCaller.createPet(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/update-pet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<UpdatePetResponse> updatePet(@Valid @RequestBody UpdatePetRequest request) {
        LOG.debug("PUT /pets/update-pet {}", request);

        final UpdatePetResponse response = petApiCaller.updatePet(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/delete-pet", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<Void> deletePet(@RequestParam("id") Long id) {
        LOG.debug("DELETE /pets/delete-pet");

        final DeletePetRequest request = new DeletePetRequest(new PetId(id));
        petApiCaller.deletePet(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/pets", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<PagedDTO<PetsResponse>> pets(@RequestParam("drop") Integer drop, @RequestParam("take") Integer take) {
        LOG.debug("GET /pets/pets");

        final PetsRequest request = new PetsRequest(drop, take);
        final PagedDTO<PetsResponse> response = petApiCaller.pets(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/pets-by-type", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<List<PetsByTypeResponse>> petsByType(@RequestParam("petType") PetType petType) {
        LOG.debug("GET /pets/pets-by-type");

        final PetsByTypeRequest request = new PetsByTypeRequest(petType);
        final List<PetsByTypeResponse> response = petApiCaller.petsByType(request);
        return ResponseEntity.ok().body(response);
    }
}
