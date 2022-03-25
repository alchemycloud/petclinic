package co.aleksa.administration.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StopWatch {
    private static final Logger LOG = LoggerFactory.getLogger(StopWatch.class);

    private final String name;

    private final long startTime;

    public StopWatch(String name) {
        this.name = name;
        this.startTime = System.currentTimeMillis();
    }

    public void stop() {
        final long stopTime = System.currentTimeMillis();
        LOG.info(name + " took " + (stopTime - startTime) + "ms");
    }
}
