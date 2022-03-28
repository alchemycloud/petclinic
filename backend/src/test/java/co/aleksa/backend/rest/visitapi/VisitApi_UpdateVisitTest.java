package co.aleksa.backend.rest.visitapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.aleksa.backend.AbstractApiTest;
import co.aleksa.backend.api.dto.visitapi.UpdateVisitRequest;
import co.aleksa.backend.api.dto.visitapi.UpdateVisitResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class VisitApi_UpdateVisitTest extends AbstractApiTest {

    @Test
    public void testUpdateVisit_updateVisit() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final UpdateVisitResponse response = updateVisit(new UpdateVisitRequest(null, null, null, null, null, null, null, null));

        // expected results
        assertObject(response);
    }
}
