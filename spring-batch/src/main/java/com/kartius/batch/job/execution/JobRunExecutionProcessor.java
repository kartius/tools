package com.kartius.batch.job.execution;

import com.kartius.batch.model.JobRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class JobRunExecutionProcessor implements ItemProcessor<JobRun, JobRun> {

    private static final Logger LOG = LoggerFactory.getLogger(JobRunExecutionProcessor.class);

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public JobRun process(JobRun jobRun) {
        //make process in batch
        return jobRun;
    }

}
