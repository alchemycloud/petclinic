package co.drytools.backend.rest.petapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.petapi.FindPetbyTypeResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class PetApi_FindPetbyTypeTest extends AbstractApiTest {

    @Test
    public void testFindPetbyType_findPetbyType() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final List<FindPetbyTypeResponse> response = findPetbyType(null);

        // expected results
        assertObject(response);
    }
}
