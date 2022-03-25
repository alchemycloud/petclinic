package co.aleksa.backend.rest.vetapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.backend.AbstractApiTest;
import co.aleksa.backend.api.dto.vetapi.VetWithSpecialtiesDTO;
import java.util.List;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class VetApi_VetInfoTest extends AbstractApiTest {

    @Test
    public void testVetInfo_vetInfo() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final List<VetWithSpecialtiesDTO> response = vetInfo(null);

        // expected results
        assertObject(response);
    }
}
