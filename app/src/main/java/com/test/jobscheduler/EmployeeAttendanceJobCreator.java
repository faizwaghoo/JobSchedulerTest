package com.test.jobscheduler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class EmployeeAttendanceJobCreator implements JobCreator {

    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        switch (tag) {
            case AutoDayOutJob.TAG:
                return new AutoDayOutJob();
            default:
                return null;
        }
    }
}
