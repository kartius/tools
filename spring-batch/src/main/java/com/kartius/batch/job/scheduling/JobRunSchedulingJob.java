package com.kartius.batch.job.scheduling;

import com.kartius.batch.job.AbstractJob;
import com.kartius.batch.model.Job;
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
public class JobRunSchedulingJob extends AbstractJob {

    private static final Logger LOG = LoggerFactory.getLogger(JobRunSchedulingJob.class);
    private static final String JOB_NAME = "JobRunSchedulingJob";

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    protected JobRunSchedulingReader jobRunSchedulingReader;

    @Autowired
    protected JobRunSchedulingWriter jobRunSchedulingWriter;

    @Autowired
    protected JobRunSchedulingProcessor jobRunSchedulingProcessor;

    @Value("${schedulingJobCron}")
    private String cron;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            final JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            jobParametersBuilder.addString("executionTime", getCurrentFormattedDateTime());
            final JobParameters jobParameters = jobParametersBuilder.toJobParameters();
            final JobExecution execution = jobLauncher.run(prolongExecutionJob(), jobParameters);

            LOG.info("JobRunSchedulingJob result: {}", execution.getStatus());
        } catch (Exception ex) {
            LOG.error("Failed to execute the ProlongRecordsJob!", ex);
            throw new JobExecutionException(ex);
        }
    }

    @Bean
    public org.springframework.batch.core.Job prolongExecutionJob() throws Exception {
        LOG.debug("Building JobRunSchedulingJob on thread {}", Thread.currentThread().getName());
        return super.buildJob();
    }

    @Bean
    public Step prolongExecutionJobStep() throws Exception {
        return super.buildJobsStep();
    }

    @Bean
    public JobDetail jobBDetails1() {
        return JobBuilder.newJob(JobRunSchedulingJob.class).withIdentity(JobRunSchedulingJob.JOB_NAME).storeDurably().build();
    }

    @Bean
    public Trigger jobBTrigger1(JobDetail jobBDetails1) {
        return TriggerBuilder.newTrigger().forJob(jobBDetails1).withIdentity("scheduleTrigger1")
                .withSchedule(CronScheduleBuilder.cronSchedule(this.cron)).build();
    }

    @Override
    protected ItemProcessor<Job, Job> itemProcessor() {
        return jobRunSchedulingProcessor;
    }

    @Override
    protected JdbcPagingItemReader<Job> itemReader() throws Exception {
        return jobRunSchedulingReader.getSchedulingItemReader("Overridden by expression");
    }

    @Override
    protected JdbcBatchItemWriter<Job> itemWriter() {
        return jobRunSchedulingWriter.getSchedulingItemWriter();
    }

    @Override
    protected String getJobName() {
        return JOB_NAME;
    }
}
