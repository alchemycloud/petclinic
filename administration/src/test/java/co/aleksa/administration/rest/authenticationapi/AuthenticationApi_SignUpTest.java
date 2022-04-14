package co.aleksa.administration.rest.authenticationapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.administration.AbstractApiTest;
import co.aleksa.administration.api.dto.authenticationapi.SignUpRequest;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class AuthenticationApi_SignUpTest extends AbstractApiTest {

    @Test
    public void testSignUp_signUp() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        signUp(new SignUpRequest(null, null, null, null, null, null));

        // expected results

    }
}
