package com.kartius.batch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.kartius.batch.util.Constants.PICK_UP_TIME_LIMIT;


public class DateTimeHelper {

    private static final Logger LOG = LoggerFactory.getLogger(DateTimeHelper.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    public static String getCurrentFormattedDateTime() {
        return LocalDateTime.now().format(formatter);
    }

    public static String getTimeWithPastOffset(final String executionTimeString) {
        final LocalDateTime executionTime = LocalDateTime.parse(executionTimeString, formatter);
        return formatter.format(executionTime.minusHours(PICK_UP_TIME_LIMIT));
    }

    public static String verifyExecutionTime(String executionTimeString) {
        if (executionTimeString == null) {
            final String currentFormattedDateTime = getCurrentFormattedDateTime();
            LOG.warn("Execution time is null. Should be used the current date time: {}", currentFormattedDateTime);
            return currentFormattedDateTime;
        }
        return executionTimeString;
    }
}
