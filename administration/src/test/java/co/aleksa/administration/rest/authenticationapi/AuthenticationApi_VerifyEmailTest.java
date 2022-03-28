package co.aleksa.administration.rest.authenticationapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.administration.AbstractApiTest;
import co.aleksa.administration.api.dto.authenticationapi.VerifyEmailRequest;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class AuthenticationApi_VerifyEmailTest extends AbstractApiTest {

    @Test
    public void testVerifyEmail_verifyEmail() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        verifyEmail(new VerifyEmailRequest(null));

        // expected results

    }
}
