package co.aleksa.administration.rest.tenantapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.administration.AbstractApiTest;
import co.aleksa.administration.api.dto.tenantapi.UpdateTenantRequest;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class TenantApi_UpdateTenantTest extends AbstractApiTest {

    @Test
    public void testUpdateTenant_updateTenant() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        updateTenant(new UpdateTenantRequest(null));

        // expected results

    }
}
