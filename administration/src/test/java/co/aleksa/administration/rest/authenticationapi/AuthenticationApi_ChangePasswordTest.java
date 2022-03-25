package co.aleksa.administration.rest.authenticationapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.administration.AbstractApiTest;
import co.aleksa.administration.api.dto.authenticationapi.ChangePasswordRequest;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class AuthenticationApi_ChangePasswordTest extends AbstractApiTest {

    @Test
    public void testChangePassword_changePassword() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        changePassword(new ChangePasswordRequest(null, null));

        // expected results

    }
}
