package co.aleksa.administration.api;

import co.aleksa.administration.api.dto.tenantapi.CreateTenantRequest;
import co.aleksa.administration.api.dto.tenantapi.CreateTenantResponse;
import co.aleksa.administration.api.dto.tenantapi.ReadTenantRequest;
import co.aleksa.administration.api.dto.tenantapi.ReadTenantResponse;
import co.aleksa.administration.api.dto.tenantapi.SearchTenantsRequest;
import co.aleksa.administration.api.dto.tenantapi.SearchTenantsResponse;
import co.aleksa.administration.api.dto.tenantapi.UpdateTenantRequest;
import co.aleksa.administration.model.Tenant;
import co.aleksa.administration.model.id.TenantId;
import co.aleksa.administration.repository.TenantRepository;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TenantApi {
    private static final Logger LOG = LoggerFactory.getLogger(TenantApi.class);

    @Inject private TenantRepository tenantRepository;

    public CreateTenantResponse createTenant(CreateTenantRequest dto) {
        LOG.trace("createTenant");
        // TODO check security constraints

        final String identifier = dto.getIdentifier();
        final String name = dto.getName();
        final Tenant model = tenantRepository.create(null, null);

        final String responseIdentifier = model.getIdentifier();
        return new CreateTenantResponse(responseIdentifier);
    }

    public void updateTenant(UpdateTenantRequest dto) {
        LOG.trace("updateTenant");
        // TODO check security constraints

        final Tenant model = new Tenant();
        model.setName(dto.getName());
        tenantRepository.update(model);
    }

    public ReadTenantResponse readTenant(ReadTenantRequest dto) {
        LOG.trace("readTenant");
        // TODO check security constraints

        final Tenant model = tenantRepository.readTenant(dto.getIdentifier()).get();
        final TenantId responseId = model.getId();
        final String responseName = model.getName();
        final String responseIdentifier = model.getIdentifier();
        return new ReadTenantResponse(responseId, responseName, responseIdentifier);
    }

    public List<SearchTenantsResponse> searchTenants(SearchTenantsRequest dto) {
        LOG.trace("searchTenants");
        // TODO check security constraints

        final List<Tenant> models = tenantRepository.searchTenants(Optional.ofNullable(dto.getName()));
        return models.stream()
                .map(
                        model -> {
                            final TenantId responseId = model.getId();
                            final String responseName = model.getName();
                            final String responseIdentifier = model.getIdentifier();
                            return new SearchTenantsResponse(responseId, responseName, responseIdentifier);
                        })
                .toList();
    }
}
