package com.test.jobscheduler;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.evernote.android.job.DailyJob;
import com.evernote.android.job.JobRequest;

import java.util.concurrent.TimeUnit;

public class AutoDayOutJob extends DailyJob {
    static final String TAG = "autoDayOutJob";
    private Context mCtx;

    @NonNull
    @Override
    protected DailyJobResult onRunDailyJob(@NonNull Params params) {
        // run your job here
        mCtx = getContext();
        SharedPreferences sharedPrefs = mCtx.getSharedPreferences("configVars", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt("keyButtonState", 2);
        editor.apply();
        return DailyJobResult.SUCCESS;
    }

    public static void scheduleExact(long scheduledTs) {
        /*new JobRequest.Builder(AutoDayOutJob.TAG)
                .setExact(scheduledTs)
                .setUpdateCurrent(true)
                .build()
                .schedule();*/
        DailyJob.schedule(new JobRequest.Builder(TAG), TimeUnit.HOURS.toMillis(7), TimeUnit.HOURS.toMillis(8));
    }
}