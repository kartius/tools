package com.kartius.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.OnProcessError;
import org.springframework.stereotype.Component;

@Component
public class JobRunItemProcessListener implements ItemProcessListener {

    private static final Logger LOG = LoggerFactory.getLogger(JobRunItemProcessListener.class);

    private int processedItemsCount = 0;

    @BeforeProcess
    @Override
    public void beforeProcess(Object item) {
        LOG.info("Beginning to process item {} on thread {}", item, Thread.currentThread().getName());
    }

    @AfterProcess
    @Override
    public void afterProcess(Object item, Object result) {
        LOG.info("Finished processing item {} on thread {}, total processed item count is {}", item, Thread.currentThread().getName(), ++processedItemsCount);
    }

    @OnProcessError
    @Override
    public void onProcessError(Object item, Exception e) {
        LOG.error("Processing error for item {}!", item, e);
    }

}
