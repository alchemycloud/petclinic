package co.drytools.backend.rest.vetapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.vetapi.UpdateVetResponse;
import co.drytools.backend.rest.dto.vetapi.RestUpdateVetRequest;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class VetApi_UpdateVetTest extends AbstractApiTest {

    @Test
    public void testUpdateVet_updateVet() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final UpdateVetResponse response = updateVet(null, new RestUpdateVetRequest(null, null));

        // expected results
        assertObject(response);
    }
}
