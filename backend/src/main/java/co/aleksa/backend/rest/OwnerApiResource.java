package co.aleksa.backend.rest;

import co.aleksa.backend.api.OwnerApiCaller;
import co.aleksa.backend.api.dto.PagedDTO;
import co.aleksa.backend.api.dto.ownerapi.AllOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.AllOwnersResponse;
import co.aleksa.backend.api.dto.ownerapi.CreateOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.CreateOwnersResponse;
import co.aleksa.backend.api.dto.ownerapi.DeleteOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.EnrichedOwnerDTO;
import co.aleksa.backend.api.dto.ownerapi.ForAddressRequest;
import co.aleksa.backend.api.dto.ownerapi.MyPetsResponse;
import co.aleksa.backend.api.dto.ownerapi.OwnerVetsResponse;
import co.aleksa.backend.api.dto.ownerapi.OwnersPetsRequest;
import co.aleksa.backend.api.dto.ownerapi.OwnersPetsResponse;
import co.aleksa.backend.api.dto.ownerapi.ReadOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.ReadOwnersResponse;
import co.aleksa.backend.api.dto.ownerapi.UpdateOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.UpdateOwnersResponse;
import co.aleksa.backend.model.id.OwnerId;
import java.util.List;
import java.util.Optional;
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
@RequestMapping("/api/owners")
public class OwnerApiResource {
    private static final Logger LOG = LoggerFactory.getLogger(OwnerApiResource.class);

    @Inject private OwnerApiCaller ownerApiCaller;

    @GetMapping(value = "/read-owners", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<ReadOwnersResponse> readOwners(@RequestParam("id") Long id) {
        LOG.debug("GET /owners/read-owners");

        final ReadOwnersRequest request = new ReadOwnersRequest(new OwnerId(id));
        final ReadOwnersResponse response = ownerApiCaller.readOwners(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/create-owners", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<CreateOwnersResponse> createOwners(@Valid @RequestBody CreateOwnersRequest request) {
        LOG.debug("POST /owners/create-owners {}", request);

        final CreateOwnersResponse response = ownerApiCaller.createOwners(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/update-owners", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<UpdateOwnersResponse> updateOwners(@Valid @RequestBody UpdateOwnersRequest request) {
        LOG.debug("PUT /owners/update-owners {}", request);

        final UpdateOwnersResponse response = ownerApiCaller.updateOwners(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/delete-owners", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<Void> deleteOwners(@RequestParam("id") Long id) {
        LOG.debug("DELETE /owners/delete-owners");

        final DeleteOwnersRequest request = new DeleteOwnersRequest(new OwnerId(id));
        ownerApiCaller.deleteOwners(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/all-owners", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<PagedDTO<AllOwnersResponse>> allOwners(@RequestParam("drop") Integer drop, @RequestParam("take") Integer take) {
        LOG.debug("GET /owners/all-owners");

        final AllOwnersRequest request = new AllOwnersRequest(drop, take);
        final PagedDTO<AllOwnersResponse> response = ownerApiCaller.allOwners(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/for-address", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<PagedDTO<EnrichedOwnerDTO>> forAddress(
            @RequestParam(value = "address", required = false) Optional<String> address,
            @RequestParam("drop") Integer drop,
            @RequestParam("take") Integer take) {
        LOG.debug("GET /owners/for-address");

        final ForAddressRequest request = new ForAddressRequest(address.orElse(null), drop, take);
        final PagedDTO<EnrichedOwnerDTO> response = ownerApiCaller.forAddress(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/owners-with-pets", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<List<EnrichedOwnerDTO>> ownersWithPets() {
        LOG.debug("GET /owners/owners-with-pets");

        final List<EnrichedOwnerDTO> response = ownerApiCaller.ownersWithPets();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/{ownerId}/pets", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<List<OwnersPetsResponse>> ownersPets(@PathVariable Long ownerId) {
        LOG.debug("GET /owners/{}/pets", ownerId);

        final OwnersPetsRequest request = new OwnersPetsRequest(new OwnerId(ownerId));
        final List<OwnersPetsResponse> response = ownerApiCaller.ownersPets(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/my-pets", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<List<MyPetsResponse>> myPets() {
        LOG.debug("GET /owners/my-pets");

        final List<MyPetsResponse> response = ownerApiCaller.myPets();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/owner-vets", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<List<OwnerVetsResponse>> ownerVets() {
        LOG.debug("GET /owners/owner-vets");

        final List<OwnerVetsResponse> response = ownerApiCaller.ownerVets();
        return ResponseEntity.ok().body(response);
    }
}
