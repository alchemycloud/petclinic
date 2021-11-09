package co.drytools.backend.rest.userapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.userapi.UserResponseDTO;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class UserApi_GetActiveUserTest extends AbstractApiTest {

    @Test
    public void testGetActiveUser_getActiveUser() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final UserResponseDTO response = getActiveUser(null, null, null, null, null, null);

        // expected results
        assertObject(response);
    }
}
