package co.aleksa.backend.rest.ownerapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.backend.AbstractApiTest;
import co.aleksa.backend.api.dto.ownerapi.MyPetsResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class OwnerApi_MyPetsTest extends AbstractApiTest {

    @Test
    public void testMyPets_myPets() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final List<MyPetsResponse> response = myPets();

        // expected results
        assertObject(response);
    }
}
