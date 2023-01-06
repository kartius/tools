package com.kartius.batch.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class JobGroup {
    private final Integer jobGroupId;
    private final String jobGroupName;
    private final String jobGroupDescription;
    private final String endpointUrl;
    private final LocalDateTime lastUpdatedDate;
    private final String lastUpdatedBy;
    private final Integer isDeleted;
    private final LocalDateTime createdDate;
    private final String createdBy;
}
