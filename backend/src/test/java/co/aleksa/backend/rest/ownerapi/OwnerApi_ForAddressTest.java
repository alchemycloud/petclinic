package co.aleksa.backend.rest.ownerapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.backend.AbstractApiTest;
import co.aleksa.backend.api.dto.PagedDTO;
import co.aleksa.backend.api.dto.ownerapi.EnrichedOwnerDTO;
import java.util.Optional;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class OwnerApi_ForAddressTest extends AbstractApiTest {

    @Test
    public void testForAddress_forAddress() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final PagedDTO<EnrichedOwnerDTO> response = forAddress(Optional.empty(), null, null);

        // expected results
        assertObject(response);
    }
}
