package co.aleksa.administration.rest.tenantapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.administration.AbstractApiTest;
import co.aleksa.administration.api.dto.tenantapi.ReadTenantResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class TenantApi_ReadTenantTest extends AbstractApiTest {

    @Test
    public void testReadTenant_readTenant() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final ReadTenantResponse response = readTenant(null);

        // expected results
        assertObject(response);
    }
}
