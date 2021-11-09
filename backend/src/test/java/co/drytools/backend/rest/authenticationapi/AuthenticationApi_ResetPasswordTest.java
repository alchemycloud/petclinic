package co.drytools.backend.rest.authenticationapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.authenticationapi.ResetPasswordRequest;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class AuthenticationApi_ResetPasswordTest extends AbstractApiTest {

    @Test
    public void testResetPassword_resetPassword() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        resetPassword(new ResetPasswordRequest(null, null));

        // expected results

    }
}
