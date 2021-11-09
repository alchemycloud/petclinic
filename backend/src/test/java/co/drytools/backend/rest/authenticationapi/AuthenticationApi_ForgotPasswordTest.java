package co.drytools.backend.rest.authenticationapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.authenticationapi.ForgotPasswordRequest;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class AuthenticationApi_ForgotPasswordTest extends AbstractApiTest {

    @Test
    public void testForgotPassword_forgotPassword() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        forgotPassword(new ForgotPasswordRequest(null));

        // expected results

    }
}
