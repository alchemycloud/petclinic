package co.aleksa.backend.rest.vetapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.backend.AbstractApiTest;
import co.aleksa.backend.api.dto.vetapi.CreateVetResponse;
import co.aleksa.backend.rest.dto.vetapi.RestCreateVetRequest;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class VetApi_CreateVetTest extends AbstractApiTest {

    @Test
    public void testCreateVet_createVet() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final CreateVetResponse response = createVet(new RestCreateVetRequest(null, null, null));

        // expected results
        assertObject(response);
    }
}
