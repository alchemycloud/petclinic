package co.aleksa.backend.rest.ownerapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.backend.AbstractApiTest;
import co.aleksa.backend.api.dto.ownerapi.OwnersPetsResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class OwnerApi_OwnersPetsTest extends AbstractApiTest {

    @Test
    public void testOwnersPets_ownersPets() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final List<OwnersPetsResponse> response = ownersPets(null);

        // expected results
        assertObject(response);
    }
}
