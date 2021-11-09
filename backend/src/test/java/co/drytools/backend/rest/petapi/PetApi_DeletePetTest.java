package co.drytools.backend.rest.petapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class PetApi_DeletePetTest extends AbstractApiTest {

    @Test
    public void testDeletePet_deletePet() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        deletePet(null);

        // expected results

    }
}
