package co.drytools.backend.rest.ownerapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.ownerapi.OwnerVetsResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class OwnerApi_OwnerVetsTest extends AbstractApiTest {

    @Test
    public void testOwnerVets_ownerVets() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final List<OwnerVetsResponse> response = ownerVets();

        // expected results
        assertObject(response);
    }
}
