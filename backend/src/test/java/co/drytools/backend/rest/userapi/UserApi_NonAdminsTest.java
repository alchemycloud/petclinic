package co.drytools.backend.rest.userapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.userapi.NonAdminsResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class UserApi_NonAdminsTest extends AbstractApiTest {

    @Test
    public void testNonAdmins_nonAdmins() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final List<NonAdminsResponse> response = nonAdmins();

        // expected results
        assertObject(response);
    }
}
