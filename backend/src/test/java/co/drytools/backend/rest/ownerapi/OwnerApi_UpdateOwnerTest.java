package co.drytools.backend.rest.ownerapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.ownerapi.UpdateOwnerResponse;
import co.drytools.backend.rest.dto.ownerapi.RestUpdateOwnerRequest;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class OwnerApi_UpdateOwnerTest extends AbstractApiTest {

    @Test
    public void testUpdateOwner_updateOwner() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final UpdateOwnerResponse response = updateOwner(null, new RestUpdateOwnerRequest(null, null, null, null));

        // expected results
        assertObject(response);
    }
}
