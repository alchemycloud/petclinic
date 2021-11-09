package co.drytools.backend.rest.ownerapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.ownerapi.ReadOwnerResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class OwnerApi_ReadOwnerTest extends AbstractApiTest {

    @Test
    public void testReadOwner_readOwner() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final ReadOwnerResponse response = readOwner(null);

        // expected results
        assertObject(response);
    }
}
