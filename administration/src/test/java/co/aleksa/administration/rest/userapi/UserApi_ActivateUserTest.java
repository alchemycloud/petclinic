package co.aleksa.administration.rest.userapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.administration.AbstractApiTest;
import co.aleksa.administration.api.dto.userapi.UserActivationDTO;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class UserApi_ActivateUserTest extends AbstractApiTest {

    @Test
    public void testActivateUser_activateUser() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        activateUser(new UserActivationDTO(null, null));

        // expected results

    }
}
