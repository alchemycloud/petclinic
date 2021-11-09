package co.drytools.backend.rest.vetapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.vetapi.VetWithSpecialtiesDTO;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class VetApi_VetInfoTest extends AbstractApiTest {

    @Test
    public void testVetInfo_vetInfo() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final VetWithSpecialtiesDTO response = vetInfo(null);

        // expected results
        assertObject(response);
    }
}
