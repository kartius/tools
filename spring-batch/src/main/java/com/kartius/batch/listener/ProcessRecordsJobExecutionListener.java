package com.kartius.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.stereotype.Component;

/**
 * The current job listener should be used for notifications
 */
@Component
public class ProcessRecordsJobExecutionListener implements JobExecutionListener {

    @BeforeJob
    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @AfterJob
    @Override
    public void afterJob(JobExecution jobExecution) {
    }

}
