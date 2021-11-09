package co.drytools.backend.rest.visitapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.visitapi.MyVisitsResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class VisitApi_MyVisitsTest extends AbstractApiTest {

    @Test // description
    public void testMyVisits_testName() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final List<MyVisitsResponse> response = myVisits(null);

        // expected results
        assertObject(response);
    }

    @Test
    public void testMyVisits_testName2() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final List<MyVisitsResponse> response = myVisits(null);

        // expected results
        assertObject(response);
    }
}
