package co.drytools.backend.rest.visitapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.PagedDTO;
import co.drytools.backend.api.dto.visitapi.VetVisitsResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class VisitApi_VetVisitsTest extends AbstractApiTest {

    @Test
    public void testVetVisits_vetVisits() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final PagedDTO<VetVisitsResponse> response = vetVisits(null, null, null);

        // expected results
        assertObject(response);
    }
}
