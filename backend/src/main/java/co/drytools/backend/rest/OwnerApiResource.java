package co.drytools.backend.rest;

import co.drytools.backend.api.OwnerApiCaller;
import co.drytools.backend.api.dto.PagedDTO;
import co.drytools.backend.api.dto.ownerapi.AllOwnersRequest;
import co.drytools.backend.api.dto.ownerapi.AllOwnersResponse;
import co.drytools.backend.api.dto.ownerapi.CreateOwnerRequest;
import co.drytools.backend.api.dto.ownerapi.CreateOwnerResponse;
import co.drytools.backend.api.dto.ownerapi.DeleteOwnerRequest;
import co.drytools.backend.api.dto.ownerapi.MyPetsResponse;
import co.drytools.backend.api.dto.ownerapi.OwnerVetsResponse;
import co.drytools.backend.api.dto.ownerapi.OwnersForAddressRequest;
import co.drytools.backend.api.dto.ownerapi.OwnersForAddressResponse;
import co.drytools.backend.api.dto.ownerapi.OwnersPetsRequest;
import co.drytools.backend.api.dto.ownerapi.OwnersPetsResponse;
import co.drytools.backend.api.dto.ownerapi.OwnersWithPetsResponse;
import co.drytools.backend.api.dto.ownerapi.ReadOwnerRequest;
import co.drytools.backend.api.dto.ownerapi.ReadOwnerResponse;
import co.drytools.backend.api.dto.ownerapi.UpdateOwnerRequest;
import co.drytools.backend.api.dto.ownerapi.UpdateOwnerResponse;
import co.drytools.backend.model.id.OwnerId;
import co.drytools.backend.rest.dto.ownerapi.RestUpdateOwnerRequest;
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
@RequestMapping("/api/")
public class OwnerApiResource {
    private static final Logger LOG = LoggerFactory.getLogger(OwnerApiResource.class);

    @Inject private OwnerApiCaller ownerApiCaller;

    @GetMapping(value = "/owner/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<ReadOwnerResponse> readOwner(@PathVariable Long id) {
        LOG.debug("GET /owner/{}", id);

        final ReadOwnerRequest request = new ReadOwnerRequest(new OwnerId(id));
        final ReadOwnerResponse response = ownerApiCaller.readOwner(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/owner", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<CreateOwnerResponse> createOwner(@Valid @RequestBody CreateOwnerRequest request) {
        LOG.debug("POST /owner {}", request);

        final CreateOwnerResponse response = ownerApiCaller.createOwner(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/owner/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<UpdateOwnerResponse> updateOwner(@PathVariable Long id, @Valid @RequestBody RestUpdateOwnerRequest restRequest) {
        LOG.trace("PUT /owner/{} {}", id, restRequest);

        final UpdateOwnerRequest request =
                new UpdateOwnerRequest(new OwnerId(id), restRequest.getUserId(), restRequest.getAddress(), restRequest.getCity(), restRequest.getTelephone());
        final UpdateOwnerResponse response = ownerApiCaller.updateOwner(request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/owner/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        LOG.debug("DELETE /owner/{}", id);

        final DeleteOwnerRequest request = new DeleteOwnerRequest(new OwnerId(id));
        ownerApiCaller.deleteOwner(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/allOwners", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<List<AllOwnersResponse>> allOwners(@RequestParam("param") Integer param) {
        LOG.debug("GET /allOwners");

        final AllOwnersRequest request = new AllOwnersRequest(param);
        final List<AllOwnersResponse> response = ownerApiCaller.allOwners(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/owners", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<PagedDTO<OwnersForAddressResponse>> ownersForAddress(
            @RequestParam(value = "address", required = false) Optional<String> address,
            @RequestParam("drop") Integer drop,
            @RequestParam("take") Integer take) {
        LOG.debug("GET /owners");

        final OwnersForAddressRequest request = new OwnersForAddressRequest(address.orElse(null), drop, take);
        final PagedDTO<OwnersForAddressResponse> response = ownerApiCaller.ownersForAddress(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/ownersWithPets", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<List<OwnersWithPetsResponse>> ownersWithPets() {
        LOG.debug("GET /ownersWithPets");

        final List<OwnersWithPetsResponse> response = ownerApiCaller.ownersWithPets();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/owner/{ownerId}/pets", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<List<OwnersPetsResponse>> ownersPets(@PathVariable Long ownerId) {
        LOG.debug("GET /owner/{}/pets", ownerId);

        final OwnersPetsRequest request = new OwnersPetsRequest(new OwnerId(ownerId));
        final List<OwnersPetsResponse> response = ownerApiCaller.ownersPets(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/my-pets", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<List<MyPetsResponse>> myPets() {
        LOG.debug("GET /my-pets");

        final List<MyPetsResponse> response = ownerApiCaller.myPets();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/owner-vets", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VET')")
    public ResponseEntity<List<OwnerVetsResponse>> ownerVets() {
        LOG.debug("GET /owner-vets");

        final List<OwnerVetsResponse> response = ownerApiCaller.ownerVets();
        return ResponseEntity.ok().body(response);
    }
}
