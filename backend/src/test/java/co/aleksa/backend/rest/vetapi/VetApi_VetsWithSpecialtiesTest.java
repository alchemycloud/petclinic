package co.aleksa.backend.rest.vetapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.backend.AbstractApiTest;
import co.aleksa.backend.api.dto.vetapi.VetWithSpecialtiesDTO;
import java.util.List;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class VetApi_VetsWithSpecialtiesTest extends AbstractApiTest {

    @Test
    public void testVetsWithSpecialties_vetsWithSpecialties() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final List<VetWithSpecialtiesDTO> response = vetsWithSpecialties();

        // expected results
        assertObject(response);
    }
}
