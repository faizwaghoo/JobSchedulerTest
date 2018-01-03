package com.test.jobscheduler;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.evernote.android.job.JobManager;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCtx = this;

        setButtonStatus();
    }

    public void setButtonStatus(){
        SharedPreferences sharedPrefs = mCtx.getSharedPreferences("configVars", Context.MODE_PRIVATE);
        int attendanceState = sharedPrefs.getInt("keyButtonState", 2);
        Button btnDayIn = (Button) findViewById(R.id.btnDayIn);
        Button btnDayOut = (Button) findViewById(R.id.btnDayOut);
        switch (attendanceState){
            case 1:
                btnDayIn.setEnabled(false);
                btnDayIn.setAlpha(0.5f);
                btnDayOut.setEnabled(true);
                btnDayOut.setAlpha(1f);
                break;
            case 2:
                btnDayIn.setEnabled(true);
                btnDayIn.setAlpha(1f);
                btnDayOut.setEnabled(false);
                btnDayOut.setAlpha(0.5f);
                break;
        }
    }

    public void onButton1(View view) {
        Calendar calendar = Calendar.getInstance();
        long currentTs = calendar.getTimeInMillis();
        Log.e("TS", "Current Ts: "+currentTs);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, 1);
        long midnightTs = calendar.getTimeInMillis();
        Log.e("TS", "Midnight Ts: "+midnightTs);
        long fifteenMinutesInTs = 900000;
        if(midnightTs - currentTs < fifteenMinutesInTs){
            Log.e("TS", "Difference < 15 mins");
            /*If difference is less than 15 mins, we schedule a job to be executed in 15 mins time
            This is a limitation explained here -
            https://github.com/evernote/android-job/wiki/FAQ#why-cant-an-interval-be-smaller-than-15-minutes-for-periodic-jobs*/
            AutoDayOutJob.scheduleExact(fifteenMinutesInTs);
        } else {
            Log.e("TS", "Difference >= 15 mins");
            AutoDayOutJob.scheduleExact(midnightTs);
        }
        SharedPreferences sharedPrefs = getSharedPreferences("configVars", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt("keyButtonState", 1);
        editor.apply();
        setButtonStatus();
    }

    public void onButton2(View view) {
        JobManager.instance().cancelAllForTag(AutoDayOutJob.TAG);
        SharedPreferences sharedPrefs = getSharedPreferences("configVars", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt("keyButtonState", 2);
        editor.apply();
        setButtonStatus();
    }
}