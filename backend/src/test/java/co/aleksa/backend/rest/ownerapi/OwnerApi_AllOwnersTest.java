package co.aleksa.backend.rest.ownerapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.backend.AbstractApiTest;
import co.aleksa.backend.api.dto.PagedDTO;
import co.aleksa.backend.api.dto.ownerapi.AllOwnersResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class OwnerApi_AllOwnersTest extends AbstractApiTest {

    @Test
    public void testAllOwners_allOwners() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final PagedDTO<AllOwnersResponse> response = allOwners(null, null);

        // expected results
        assertObject(response);
    }
}
