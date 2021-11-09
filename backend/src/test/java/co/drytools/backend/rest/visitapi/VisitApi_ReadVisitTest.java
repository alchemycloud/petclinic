package co.drytools.backend.rest.visitapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.visitapi.ReadVisitResponse;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class VisitApi_ReadVisitTest extends AbstractApiTest {

    @Test
    public void testReadVisit_readVisit() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final ReadVisitResponse response = readVisit(null);

        // expected results
        assertObject(response);
    }
}
