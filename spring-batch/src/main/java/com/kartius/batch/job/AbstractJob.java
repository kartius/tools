package com.kartius.batch.job;

import com.kartius.batch.listener.JobRunItemProcessListener;
import com.kartius.batch.listener.JobRunSkipPolicy;
import com.kartius.batch.listener.ProcessRecordsJobExecutionListener;
import com.kartius.batch.model.JobRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import static com.kartius.batch.util.Constants.PAGE_SIZE;


@Configuration
@EnableBatchProcessing
public abstract class AbstractJob implements org.quartz.Job {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractJob.class);

    @Autowired
    protected JobBuilderFactory jobBuilderFactory;

    @Autowired
    protected StepBuilderFactory stepBuilderFactory;

    @Autowired
    protected JobRunItemProcessListener jobRunItemProcessListener;

    @Autowired
    protected JobRunSkipPolicy jobRunSkipPolicy;

    @Autowired
    protected ProcessRecordsJobExecutionListener processRecordsJobExecutionListener;

    @Autowired
    protected TaskExecutor taskExecutor;

    @Autowired
    protected CompositeItemProcessorBuilder<JobRun, JobRun> compositeItemProcessorBuilder;

    public Job buildJob() throws Exception {
        LOG.debug("Building processScheduledCallsJob on thread {}", Thread.currentThread().getName());

        return this.jobBuilderFactory.get(getJobName())
                .incrementer(new RunIdIncrementer())
                .listener(processRecordsJobExecutionListener)
                .flow(buildJobsStep())
                .end().build();
    }

    public Step buildJobsStep() throws Exception {
        return this.stepBuilderFactory.get(createJobStepName())
                .<JobRun, JobRun>chunk(PAGE_SIZE)
                .reader(itemReader())
                .processor(compositeItemProcessor())
                .listener(jobRunItemProcessListener)
                .writer(itemWriter())
                .faultTolerant()
                .skipPolicy(jobRunSkipPolicy)
                .taskExecutor(taskExecutor)
                .build();
    }

    private CompositeItemProcessor<JobRun, JobRun> compositeItemProcessor() {
        return this.compositeItemProcessorBuilder
                .delegates(itemProcessor())   //can chain these
                .build();
    }

    private String createJobStepName(){
        return String.format("%s%s",getJobName(),"Step");
    }

    protected abstract ItemProcessor itemProcessor();

    protected abstract JdbcPagingItemReader itemReader() throws Exception;

    protected abstract JdbcBatchItemWriter itemWriter();

    protected abstract String getJobName();

}
