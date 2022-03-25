package co.aleksa.administration.rest.userapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.administration.AbstractApiTest;
import co.aleksa.administration.api.dto.userapi.GetActiveUserResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class UserApi_GetActiveUserTest extends AbstractApiTest {

    @Test
    public void testGetActiveUser_getActiveUser() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final GetActiveUserResponse response = getActiveUser(null);

        // expected results
        assertObject(response);
    }
}
