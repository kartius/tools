package com.kartius.batch.listener;

import com.kartius.batch.exception.FailedToCallEndpointException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.stereotype.Component;

import static com.kartius.batch.util.Constants.SKIP_LIMIT;


@Component
public class JobRunSkipPolicy implements SkipPolicy {

    private static final Logger LOG = LoggerFactory.getLogger(JobRunSkipPolicy.class);

    @Override
    public boolean shouldSkip(Throwable t, int skipCount) throws SkipLimitExceededException {
        if (t instanceof FailedToCallEndpointException) {
            LOG.warn("Failed to call the specified endpoint {} times!", skipCount + 1);
            if (skipCount > SKIP_LIMIT) {
                LOG.error("Skip limit exceeded, terminating the job...");
                return false;
            }
            return true;
        }
        return false;
    }

}
