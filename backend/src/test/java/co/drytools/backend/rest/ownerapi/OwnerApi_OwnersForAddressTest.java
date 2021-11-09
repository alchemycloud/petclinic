package co.drytools.backend.rest.ownerapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.PagedDTO;
import co.drytools.backend.api.dto.ownerapi.OwnersForAddressResponse;
import java.util.Optional;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class OwnerApi_OwnersForAddressTest extends AbstractApiTest {

    @Test
    public void testOwnersForAddress_ownersForAddress() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final PagedDTO<OwnersForAddressResponse> response = ownersForAddress(Optional.empty(), null, null);

        // expected results
        assertObject(response);
    }
}
