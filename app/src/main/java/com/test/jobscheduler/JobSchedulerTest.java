package com.test.jobscheduler;

import android.app.Application;

import com.evernote.android.job.JobManager;

public class JobSchedulerTest extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new EmployeeAttendanceJobCreator());
    }
}