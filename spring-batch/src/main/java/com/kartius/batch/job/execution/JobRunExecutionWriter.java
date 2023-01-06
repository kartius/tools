package com.kartius.batch.job.execution;

import com.kartius.batch.model.JobRun;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JobRunExecutionWriter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcBatchItemWriter<JobRun> getExecutionItemWriter() {

        return new JdbcBatchItemWriterBuilder<JobRun>()
                .dataSource(this.dataSource)
                .sql(UPDATE_JOB_EXECUTION_SQL)
                .beanMapped()
                .build();
    }

    public static final String UPDATE_JOB_EXECUTION_SQL = "UPDATE job_run SET execution_status = :executionStatus, executed_at = :executedAt WHERE job_run_id = :jobRunId";

}
