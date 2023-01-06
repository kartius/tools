package com.kartius.batch.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class JobRun {
    private final Integer jobRunId;
    private final Integer jobId;
    private final LocalDateTime plannedExecutionDateTime;
    @Setter
    private LocalDateTime executedAt;
    @Setter
    private String executionStatus;
    private final Integer isDeleted;
    private final LocalDateTime deletedDate;
    private final String deletedBy;
}
