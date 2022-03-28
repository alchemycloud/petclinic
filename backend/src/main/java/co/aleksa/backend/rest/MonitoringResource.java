package co.aleksa.backend.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MonitoringResource {
    private static final Logger LOG = LoggerFactory.getLogger(MonitoringResource.class);

    @RequestMapping(value = "/health-check", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> healthCheck() {
        LOG.debug("GET /health-check");

        return ResponseEntity.ok().build();
    }
}
