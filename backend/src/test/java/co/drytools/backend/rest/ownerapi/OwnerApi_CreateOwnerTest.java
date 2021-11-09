package co.drytools.backend.rest.ownerapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.ownerapi.CreateOwnerRequest;
import co.drytools.backend.api.dto.ownerapi.CreateOwnerResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class OwnerApi_CreateOwnerTest extends AbstractApiTest {

    @Test
    public void testCreateOwner_createOwner() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final CreateOwnerResponse response = createOwner(new CreateOwnerRequest(null, null, null, null));

        // expected results
        assertObject(response);
    }
}
