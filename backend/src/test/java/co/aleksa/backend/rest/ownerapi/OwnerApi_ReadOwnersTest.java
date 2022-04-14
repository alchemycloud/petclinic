package co.aleksa.backend.rest.ownerapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.backend.AbstractApiTest;
import co.aleksa.backend.api.dto.ownerapi.ReadOwnersResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class OwnerApi_ReadOwnersTest extends AbstractApiTest {

    @Test
    public void testReadOwners_readOwners() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final ReadOwnersResponse response = readOwners(null);

        // expected results
        assertObject(response);
    }
}
