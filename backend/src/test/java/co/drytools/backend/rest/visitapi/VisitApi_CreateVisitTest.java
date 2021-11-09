package co.drytools.backend.rest.visitapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.visitapi.CreateVisitRequest;
import co.drytools.backend.api.dto.visitapi.CreateVisitResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class VisitApi_CreateVisitTest extends AbstractApiTest {

    @Test
    public void testCreateVisit_createVisit() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final CreateVisitResponse response = createVisit(new CreateVisitRequest(null, null, null, null, null, null, null));

        // expected results
        assertObject(response);
    }
}
