package co.aleksa.administration.rest.tenantapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.administration.AbstractApiTest;
import co.aleksa.administration.api.dto.tenantapi.CreateTenantRequest;
import co.aleksa.administration.api.dto.tenantapi.CreateTenantResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class TenantApi_CreateTenantTest extends AbstractApiTest {

    @Test
    public void testCreateTenant_createTenant() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final CreateTenantResponse response = createTenant(new CreateTenantRequest(null, null));

        // expected results
        assertObject(response);
    }
}
