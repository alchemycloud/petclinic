package co.aleksa.administration.rest.authenticationapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.administration.AbstractApiTest;
import co.aleksa.administration.api.dto.authenticationapi.ResetPasswordRequest;
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
