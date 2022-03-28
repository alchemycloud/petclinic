package co.aleksa.backend.rest.petapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.backend.AbstractApiTest;
import co.aleksa.backend.api.dto.petapi.CreatePetRequest;
import co.aleksa.backend.api.dto.petapi.CreatePetResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class PetApi_CreatePetTest extends AbstractApiTest {

    @Test
    public void testCreatePet_createPet() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final CreatePetResponse response = createPet(new CreatePetRequest(null, null, null, null, null));

        // expected results
        assertObject(response);
    }
}
