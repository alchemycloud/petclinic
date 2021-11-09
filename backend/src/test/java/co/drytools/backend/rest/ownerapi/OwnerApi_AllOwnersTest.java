package co.drytools.backend.rest.ownerapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.ownerapi.AllOwnersResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class OwnerApi_AllOwnersTest extends AbstractApiTest {

    @Test
    public void testAllOwners_allOwners() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final List<AllOwnersResponse> response = allOwners(null);

        // expected results
        assertObject(response);
    }
}
