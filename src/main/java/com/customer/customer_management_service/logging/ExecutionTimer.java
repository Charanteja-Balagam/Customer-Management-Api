package com.customer.customer_management_service.logging;

import org.slf4j.Logger;

import java.time.Duration;
import java.time.Instant;

public class ExecutionTimer {
    private final Logger logger;
    private final String task;
    private final Instant start;

    public ExecutionTimer(Logger logger, String task) {
        this.logger = logger;
        this.task = task;
        this.start = Instant.now();
    }

    public void logDuration() {
        long duration = Duration.between(start, Instant.now()).toMillis();
        logger.debug("Execution time for {}: {} ms", task, duration);
    }
}
