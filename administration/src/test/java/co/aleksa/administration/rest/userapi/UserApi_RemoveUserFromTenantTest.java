package co.aleksa.administration.rest.userapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.administration.AbstractApiTest;
import co.aleksa.administration.api.dto.userapi.RemoveUserFromTenantRequest;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class UserApi_RemoveUserFromTenantTest extends AbstractApiTest {

    @Test
    public void testRemoveUserFromTenant_removeUserFromTenant() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        removeUserFromTenant(new RemoveUserFromTenantRequest(null));

        // expected results

    }
}
