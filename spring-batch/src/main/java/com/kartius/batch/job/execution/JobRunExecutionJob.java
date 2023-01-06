package com.kartius.batch.job.execution;


import com.kartius.batch.job.AbstractJob;
import com.kartius.batch.model.JobRun;
import org.springframework.batch.core.Job;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.kartius.batch.util.DateTimeHelper.getCurrentFormattedDateTime;


@Configuration
@EnableBatchProcessing
public class JobRunExecutionJob extends AbstractJob {

    private static final Logger LOG = LoggerFactory.getLogger(JobRunExecutionJob.class);
    private static final String JOB_NAME = "JobRunExecutionJob";

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    protected JobRunExecutionReader jobRunExecutionReader;

    @Autowired
    protected JobRunExecutionWriter jobRunExecutionWriter;

    @Autowired
    protected JobRunExecutionProcessor jobRunExecutionProcessor;

    @Value("${executionJobCron}")
    private String cron;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            final JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addString("executionTime", getCurrentFormattedDateTime());
            final JobParameters jobParameters = jobParametersBuilder.toJobParameters();
            final JobExecution execution = jobLauncher.run(requestEndpointJob(), jobParameters);

            LOG.info("JobRunExecutionJob result: {}", execution.getStatus());
        } catch (Exception ex) {
            LOG.error("Failed to execute the PerformRecordsJob!", ex);
            throw new JobExecutionException(ex);
        }
    }

    @Bean
    public Job requestEndpointJob() throws Exception {
        LOG.debug("Building JobRunExecutionJob on thread {}", Thread.currentThread().getName());
        return super.buildJob();
    }

    @Bean
    public Step requestEndpointJobStep() throws Exception {
        return super.buildJobsStep();
    }

    @Bean
    public JobDetail jobBDetails() {
        return JobBuilder.newJob(JobRunExecutionJob.class).withIdentity(JobRunExecutionJob.JOB_NAME).storeDurably().build();
    }

    @Bean
    public Trigger jobBTrigger(JobDetail jobBDetails) {
        return TriggerBuilder.newTrigger().forJob(jobBDetails).withIdentity("scheduleTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(this.cron))
                .build();	//0 0/15 * * * ?
    }

    @Override
    protected ItemProcessor<JobRun, JobRun> itemProcessor() {
        return jobRunExecutionProcessor;
    }

    @Override
    protected JdbcPagingItemReader<JobRun> itemReader() throws Exception {
        return jobRunExecutionReader.getExecutionItemReader("Overridden by expression");
    }

    @Override
    protected JdbcBatchItemWriter<JobRun> itemWriter() {
        return jobRunExecutionWriter.getExecutionItemWriter();
    }

    @Override
    protected String getJobName() {
        return JOB_NAME;
    }

}
