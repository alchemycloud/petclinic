package co.aleksa.administration.rest.userapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.administration.AbstractApiTest;
import co.aleksa.administration.api.dto.userapi.UpdateUserRequest;
import co.aleksa.administration.api.dto.userapi.UpdateUserResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class UserApi_UpdateUserTest extends AbstractApiTest {

    @Test
    public void testUpdateUser_updateUser() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final UpdateUserResponse response = updateUser(new UpdateUserRequest(null, null, null, null, null, null, null, null, null, null, null, null, null));

        // expected results
        assertObject(response);
    }
}
