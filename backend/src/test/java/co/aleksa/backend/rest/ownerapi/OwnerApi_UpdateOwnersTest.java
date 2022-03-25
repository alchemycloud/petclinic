package co.aleksa.backend.rest.ownerapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.backend.AbstractApiTest;
import co.aleksa.backend.api.dto.ownerapi.UpdateOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.UpdateOwnersResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class OwnerApi_UpdateOwnersTest extends AbstractApiTest {

    @Test
    public void testUpdateOwners_updateOwners() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final UpdateOwnersResponse response = updateOwners(new UpdateOwnersRequest(null, null, null, null, null));

        // expected results
        assertObject(response);
    }
}
