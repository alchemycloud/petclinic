package co.aleksa.backend.rest.vetapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.backend.AbstractApiTest;
import co.aleksa.backend.api.dto.vetapi.UpdateVetResponse;
import co.aleksa.backend.rest.dto.vetapi.RestUpdateVetRequest;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class VetApi_UpdateVetTest extends AbstractApiTest {

    @Test
    public void testUpdateVet_updateVet() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final UpdateVetResponse response = updateVet(new RestUpdateVetRequest(null, null, null, null));

        // expected results
        assertObject(response);
    }
}
