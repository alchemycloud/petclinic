package co.aleksa.backend.rest.petapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.backend.AbstractApiTest;
import co.aleksa.backend.api.dto.petapi.PetsByTypeResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class PetApi_PetsByTypeTest extends AbstractApiTest {

    @Test
    public void testPetsByType_petsByType() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final List<PetsByTypeResponse> response = petsByType(null);

        // expected results
        assertObject(response);
    }
}
