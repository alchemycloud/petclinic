package co.aleksa.backend.rest.visitapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.backend.AbstractApiTest;
import co.aleksa.backend.api.dto.PagedDTO;
import co.aleksa.backend.api.dto.visitapi.VisitDTO;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class VisitApi_VisitsByVetTest extends AbstractApiTest {

    @Test
    public void testVisitsByVet_visitsByVet() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final PagedDTO<VisitDTO> response = visitsByVet(null, null, null);

        // expected results
        assertObject(response);
    }
}
