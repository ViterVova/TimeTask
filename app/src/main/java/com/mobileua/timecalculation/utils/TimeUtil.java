package com.mobileua.timecalculation.utils;

import android.os.SystemClock;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vova on 12.07.2016.
 */
public class TimeUtil {

    private static TimeUtil instance;
    private long startAppTime;
    private Date validTime;

    private TimeUtil() {
    }

    public static TimeUtil getInstance() {
        if (instance == null) {
            instance = new TimeUtil();
        }
        return instance;
    }

    public void saveLoginTime(String url) {
        if (url != null && url != "") {
            NTPTimeUtil timeUtil = new NTPTimeUtil(url);
            validTime = timeUtil.getTimeFromServer();
            startAppTime = getCurrentSystemTime();
        }
    }

    public Date getValidTime() {
        long currentAppTime = getCurrentSystemTime();
        long durationMilliSecond = currentAppTime - startAppTime;
        startAppTime = currentAppTime;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(validTime);
        if (durationMilliSecond > Integer.MAX_VALUE) {
            long durationSecond = TimeUnit.MILLISECONDS.toSeconds(durationMilliSecond);
            if (durationSecond > Integer.MAX_VALUE) {
                long durationMinutes = TimeUnit.SECONDS.toMinutes(durationMilliSecond);
                if (durationMinutes > Integer.MAX_VALUE) {
                    long durationHour = TimeUnit.MINUTES.toHours(durationMinutes);
                    calendar.add(Calendar.HOUR, (int) durationHour);
                } else {
                    calendar.add(Calendar.MINUTE, (int) durationMinutes);
                }
            } else {
                calendar.add(Calendar.SECOND, (int) durationSecond);
            }
        } else {
            calendar.add(Calendar.MILLISECOND, (int) durationMilliSecond);
        }
        validTime = calendar.getTime();
        return validTime;
    }

    private long getCurrentSystemTime(){
        return SystemClock.elapsedRealtime();
    }
}
