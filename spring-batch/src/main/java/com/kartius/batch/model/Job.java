package com.kartius.batch.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder=true)
@EqualsAndHashCode
@ToString
public class Job {
    private final Integer jobId;
    private final Integer jobGroupId;
    private final Integer affiliateId;
    private final String timeZone;
    private final String frequency;
    private final Integer scheduledDayOfMonth;
    private final String scheduledTimeOfDay;
    private final LocalDateTime prolongFromDate;
    private final LocalDateTime lastUpdatedDate;
    private final String lastUpdatedBy;
    private final Integer isDeleted;
    private final LocalDateTime createdDate;
    private final String createdBy;
}
