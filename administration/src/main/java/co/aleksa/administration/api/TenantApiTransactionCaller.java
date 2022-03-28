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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TenantApiTransactionCaller {
    private static final Logger LOG = LoggerFactory.getLogger(TenantApiTransactionCaller.class);

    @Inject private AuditFacade auditFacade;

    @Inject private TenantApi tenantApi;

    @Transactional
    public CreateTenantResponse createTenant(CreateTenantRequest dto) {
        LOG.trace("createTenant");

        final CreateTenantResponse result = tenantApi.createTenant(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional
    public void updateTenant(UpdateTenantRequest dto) {
        LOG.trace("updateTenant");

        tenantApi.updateTenant(dto);
        auditFacade.flushInTransaction();
    }

    @Transactional(readOnly = true)
    public ReadTenantResponse readTenant(ReadTenantRequest dto) {
        LOG.trace("readTenant");

        final ReadTenantResponse result = tenantApi.readTenant(dto);
        auditFacade.flushInTransaction();
        return result;
    }

    @Transactional(readOnly = true)
    public List<SearchTenantsResponse> searchTenants(SearchTenantsRequest dto) {
        LOG.trace("searchTenants");

        final List<SearchTenantsResponse> result = tenantApi.searchTenants(dto);
        auditFacade.flushInTransaction();
        return result;
    }
}
