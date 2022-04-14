package co.aleksa.backend.rest.petapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.backend.AbstractApiTest;
import co.aleksa.backend.api.dto.petapi.UpdatePetRequest;
import co.aleksa.backend.api.dto.petapi.UpdatePetResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class PetApi_UpdatePetTest extends AbstractApiTest {

    @Test
    public void testUpdatePet_updatePet() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final UpdatePetResponse response = updatePet(new UpdatePetRequest(null, null, null, null, null, null));

        // expected results
        assertObject(response);
    }
}
