package com.kartius.batch.job.execution;

import com.kartius.batch.model.JobRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static com.kartius.batch.util.Constants.FETCH_SIZE;
import static com.kartius.batch.util.Constants.PAGE_SIZE;
import static com.kartius.batch.util.DateTimeHelper.getTimeWithPastOffset;
import static com.kartius.batch.util.DateTimeHelper.verifyExecutionTime;


@Configuration
public class JobRunExecutionReader {

    private static final Logger LOG = LoggerFactory.getLogger(JobRunExecutionReader.class);

    @Autowired
    private DataSource dataSource;

    @Bean
    @StepScope
    @Lazy
    public JdbcPagingItemReader<JobRun> getExecutionItemReader(
            @Value("#{jobParameters['executionTime']}") String executionTime) throws Exception {

        final Map<String, Order> orderById = new HashMap<>();
        orderById.put(GET_APPLICABLE_JOB_RUNS_ORDER_BY, Order.ASCENDING);

        return new JdbcPagingItemReaderBuilder<JobRun>()
                .dataSource(this.dataSource)
                .saveState(false)
                .queryProvider(jobRunQueryProvider(verifyExecutionTime(executionTime)))
                .sortKeys(orderById)
                //.rowMapper(jobMapper) use spring jdbc mapper
                .fetchSize(FETCH_SIZE)
                .pageSize(PAGE_SIZE)
                .build();
    }

    public PagingQueryProvider jobRunQueryProvider(final String executionTime) throws Exception {

        LOG.debug("Setting query time as {}", executionTime);

        SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
        factory.setSelectClause(GET_APPLICABLE_JOB_RUNS_SELECT);
        factory.setFromClause(GET_APPLICABLE_JOB_RUNS_FROM);
        factory.setWhereClause(String.format(GET_APPLICABLE_JOB_RUNS_WHERE,
                getTimeWithPastOffset(executionTime), executionTime));
        factory.setSortKey(GET_APPLICABLE_JOB_RUNS_ORDER_BY);
        factory.setDataSource(this.dataSource);
        return factory.getObject();
    }

    public static final String GET_APPLICABLE_JOB_RUNS_SELECT = "SELECT ";
    public static final String GET_APPLICABLE_JOB_RUNS_FROM = "FROM ";
    public static final String GET_APPLICABLE_JOB_RUNS_WHERE = "WHERE ";
    public static final String GET_APPLICABLE_JOB_RUNS_ORDER_BY = "job_run_id";
}
