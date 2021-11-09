package co.drytools.backend.rest.visitapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.visitapi.UpdateVisitResponse;
import co.drytools.backend.rest.dto.visitapi.RestUpdateVisitRequest;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class VisitApi_UpdateVisitTest extends AbstractApiTest {

    @Test
    public void testUpdateVisit_updateVisit() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final UpdateVisitResponse response = updateVisit(null, new RestUpdateVisitRequest(null, null, null, null, null, null, null));

        // expected results
        assertObject(response);
    }
}
