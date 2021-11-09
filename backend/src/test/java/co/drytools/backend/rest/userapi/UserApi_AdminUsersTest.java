package co.drytools.backend.rest.userapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.userapi.AdminUsersResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class UserApi_AdminUsersTest extends AbstractApiTest {

    @Test
    public void testAdminUsers_adminUsers() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final List<AdminUsersResponse> response = adminUsers();

        // expected results
        assertObject(response);
    }
}
