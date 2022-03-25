package co.aleksa.administration.rest.tenantapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.administration.AbstractApiTest;
import co.aleksa.administration.api.dto.tenantapi.SearchTenantsResponse;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class TenantApi_SearchTenantsTest extends AbstractApiTest {

    @Test
    public void testSearchTenants_searchTenants() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final List<SearchTenantsResponse> response = searchTenants(Optional.empty());

        // expected results
        assertObject(response);
    }
}
