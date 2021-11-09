package co.drytools.backend.rest.visitapi;

import static org.junit.jupiter.api.Assertions.fail;

import co.drytools.backend.AbstractApiTest;
import co.drytools.backend.api.dto.visitapi.ScheduledVisitsResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("ApiTestCategory1")
public class VisitApi_ScheduledVisitsTest extends AbstractApiTest {

    @Test
    public void testScheduledVisits_scheduledVisits() throws Exception {
        fail("Test is not implemented");
        // preconditions

        // test
        takeSnapshot();
        final List<ScheduledVisitsResponse> response = scheduledVisits();

        // expected results
        assertObject(response);
    }
}
