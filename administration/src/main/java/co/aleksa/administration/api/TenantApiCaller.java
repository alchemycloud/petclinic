package co.aleksa.administration.api;

import co.aleksa.administration.api.dto.tenantapi.CreateTenantRequest;
import co.aleksa.administration.api.dto.tenantapi.CreateTenantResponse;
import co.aleksa.administration.api.dto.tenantapi.ReadTenantRequest;
import co.aleksa.administration.api.dto.tenantapi.ReadTenantResponse;
import co.aleksa.administration.api.dto.tenantapi.SearchTenantsRequest;
import co.aleksa.administration.api.dto.tenantapi.SearchTenantsResponse;
import co.aleksa.administration.api.dto.tenantapi.UpdateTenantRequest;
import co.aleksa.administration.audit.AuditFacade;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TenantApiCaller {
    private static final Logger LOG = LoggerFactory.getLogger(TenantApiCaller.class);

    @Lazy @Inject private AuditFacade auditFacade;

    @Inject private TenantApiTransactionCaller tenantApiTransactionCaller;

    public CreateTenantResponse createTenant(CreateTenantRequest dto) {
        LOG.trace("createTenant");

        final CreateTenantResponse result = tenantApiTransactionCaller.createTenant(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public void updateTenant(UpdateTenantRequest dto) {
        LOG.trace("updateTenant");

        tenantApiTransactionCaller.updateTenant(dto);
        auditFacade.flushAfterTransaction();
    }

    public ReadTenantResponse readTenant(ReadTenantRequest dto) {
        LOG.trace("readTenant");

        final ReadTenantResponse result = tenantApiTransactionCaller.readTenant(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }

    public List<SearchTenantsResponse> searchTenants(SearchTenantsRequest dto) {
        LOG.trace("searchTenants");

        final List<SearchTenantsResponse> result = tenantApiTransactionCaller.searchTenants(dto);
        auditFacade.flushAfterTransaction();
        return result;
    }
}
