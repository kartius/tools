package com.kartius.batch.job.scheduling;

import com.kartius.batch.model.Job;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JobRunSchedulingWriter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcBatchItemWriter<Job> getSchedulingItemWriter() {
        return new JdbcBatchItemWriterBuilder<Job>()
                .dataSource(this.dataSource)
                .sql(UPDATE_JOB_EXECUTION_SQL)
                .beanMapped()
                .build();
    }

    public static final String UPDATE_JOB_EXECUTION_SQL = "UPDATE job SET last_updated_date = now() WHERE job_id = :jobId";

}
