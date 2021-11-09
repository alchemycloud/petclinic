package co.drytools.backend.rest.userapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.userapi.ReadUserResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class UserApi_ReadUserTest extends AbstractApiTest {

    @Test
    public void testReadUser_readUser() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final ReadUserResponse response = readUser(null);

        // expected results
        assertObject(response);
    }
}
