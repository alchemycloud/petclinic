package co.aleksa.backend.rest.ownerapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.backend.AbstractApiTest;
import co.aleksa.backend.api.dto.ownerapi.CreateOwnersRequest;
import co.aleksa.backend.api.dto.ownerapi.CreateOwnersResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class OwnerApi_CreateOwnersTest extends AbstractApiTest {

    @Test
    public void testCreateOwners_createOwners() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final CreateOwnersResponse response = createOwners(new CreateOwnersRequest(null, null, null, null));

        // expected results
        assertObject(response);
    }
}
