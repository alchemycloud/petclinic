package co.drytools.backend.rest.petapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.PagedDTO;
import co.drytools.backend.api.dto.petapi.PetsResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class PetApi_PetsTest extends AbstractApiTest {

    @Test
    public void testPets_pets() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final PagedDTO<PetsResponse> response = pets(null, null);

        // expected results
        assertObject(response);
    }
}
