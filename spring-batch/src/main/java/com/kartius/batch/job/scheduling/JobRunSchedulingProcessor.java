package com.kartius.batch.job.scheduling;

import com.kartius.batch.model.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class JobRunSchedulingProcessor implements ItemProcessor<Job, Job> {

    private static final Logger LOG = LoggerFactory.getLogger(JobRunSchedulingProcessor.class);

    @Override
    @Transactional
    public Job process(Job job) {
        try {
            //make process in batch
            LOG.debug("Processing of {} is complete!", job);
        } catch (Exception ex) {
            LOG.error("Could not process the {} job!", job, ex);
        }
        return job;
    }
}
