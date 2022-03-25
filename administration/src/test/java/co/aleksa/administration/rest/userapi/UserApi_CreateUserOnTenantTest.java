package co.aleksa.administration.rest.userapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.administration.AbstractApiTest;
import co.aleksa.administration.api.dto.userapi.CreateUserOnTenantRequest;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class UserApi_CreateUserOnTenantTest extends AbstractApiTest {

    @Test
    public void testCreateUserOnTenant_createUserOnTenant() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        createUserOnTenant(new CreateUserOnTenantRequest(null, null, null));

        // expected results

    }
}
