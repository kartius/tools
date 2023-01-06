package com.kartius.batch.util;

public class Constants {

    public static final String JOB_RUN_COMPLETE_STATUS = "COMPLETED";
    public static final String JOB_RUN_FAILED_STATUS = "FAILED";

    public static final int FETCH_SIZE = 4;
    public static final int PAGE_SIZE = 1;
    public static final int CORE_NUM = 2;
    public static final int MAX_THREAD_NUM = 4;
    public static final int SKIP_LIMIT = 1;
    public static final int PICK_UP_TIME_LIMIT = 12;

    private Constants() {
        // Private constructor to prevent instantiation
    }
}
