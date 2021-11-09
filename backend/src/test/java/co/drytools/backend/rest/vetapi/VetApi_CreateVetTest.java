package co.drytools.backend.rest.vetapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.vetapi.CreateVetResponse;
import co.drytools.backend.rest.dto.vetapi.RestCreateVetRequest;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class VetApi_CreateVetTest extends AbstractApiTest {

    @Test
    public void testCreateVet_createVet() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final CreateVetResponse response = createVet(new RestCreateVetRequest(null, null));

        // expected results
        assertObject(response);
    }
}
