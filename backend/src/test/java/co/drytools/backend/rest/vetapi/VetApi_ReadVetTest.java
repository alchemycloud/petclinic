package co.drytools.backend.rest.vetapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.vetapi.ReadVetResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class VetApi_ReadVetTest extends AbstractApiTest {

    @Test
    public void testReadVet_readVet() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final ReadVetResponse response = readVet(null);

        // expected results
        assertObject(response);
    }
}
