package com.mobileua.timecalculation.utils;

import android.util.Log;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Vova on 12.07.2016.
 */
public class NTPTimeUtil {

    private static final String TAG = "NTPTimeUtil";
    private String url;
    private Date lastSyncTime;


    public NTPTimeUtil(String url) {
        this.url = url;
    }

    public Date getTimeFromServer() {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Future<Date> syncTime = pool.submit(new SyncTimeThread(url));
        try {
            lastSyncTime = syncTime.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, e.getMessage());
        }
        return lastSyncTime;
    }
}
