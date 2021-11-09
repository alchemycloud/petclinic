package co.drytools.backend.rest.petapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.petapi.ReadPetResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class PetApi_ReadPetTest extends AbstractApiTest {

    @Test
    public void testReadPet_readPet() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final ReadPetResponse response = readPet(null);

        // expected results
        assertObject(response);
    }
}
