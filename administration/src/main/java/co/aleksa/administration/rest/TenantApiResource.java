package co.aleksa.administration.rest;

import co.aleksa.administration.api.TenantApiCaller;
import co.aleksa.administration.api.dto.tenantapi.CreateTenantRequest;
import co.aleksa.administration.api.dto.tenantapi.CreateTenantResponse;
import co.aleksa.administration.api.dto.tenantapi.ReadTenantRequest;
import co.aleksa.administration.api.dto.tenantapi.ReadTenantResponse;
import co.aleksa.administration.api.dto.tenantapi.SearchTenantsRequest;
import co.aleksa.administration.api.dto.tenantapi.SearchTenantsResponse;
import co.aleksa.administration.api.dto.tenantapi.UpdateTenantRequest;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenants")
public class TenantApiResource {
    private static final Logger LOG = LoggerFactory.getLogger(TenantApiResource.class);

    @Inject private TenantApiCaller tenantApiCaller;

    @PostMapping(value = "/create-tenant", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<CreateTenantResponse> createTenant(@Valid @RequestBody CreateTenantRequest request) {
        LOG.debug("POST /tenants/create-tenant {}", request);

        final CreateTenantResponse response = tenantApiCaller.createTenant(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/update-tenant", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<Void> updateTenant(@Valid @RequestBody UpdateTenantRequest request) {
        LOG.debug("PUT /tenants/update-tenant {}", request);

        tenantApiCaller.updateTenant(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/read-tenant", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<ReadTenantResponse> readTenant(@RequestParam("identifier") String identifier) {
        LOG.debug("GET /tenants/read-tenant");

        final ReadTenantRequest request = new ReadTenantRequest(identifier);
        final ReadTenantResponse response = tenantApiCaller.readTenant(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/search-tenants", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<List<SearchTenantsResponse>> searchTenants(@RequestParam(value = "name", required = false) Optional<String> name) {
        LOG.debug("GET /tenants/search-tenants");

        final SearchTenantsRequest request = new SearchTenantsRequest(name.orElse(null));
        final List<SearchTenantsResponse> response = tenantApiCaller.searchTenants(request);
        return ResponseEntity.ok().body(response);
    }
}
