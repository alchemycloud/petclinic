package co.drytools.backend.rest.petapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.petapi.UpdatePetResponse;
import co.drytools.backend.rest.dto.petapi.RestUpdatePetRequest;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class PetApi_UpdatePetTest extends AbstractApiTest {

    @Test
    public void testUpdatePet_updatePet() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final UpdatePetResponse response = updatePet(null, new RestUpdatePetRequest(null, null, null, null, null));

        // expected results
        assertObject(response);
    }
}
