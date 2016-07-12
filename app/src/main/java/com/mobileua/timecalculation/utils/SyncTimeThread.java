package com.mobileua.timecalculation.utils;

import android.util.Log;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * Created by Vova on 12.07.2016.
 */
public class SyncTimeThread implements Callable<Date> {

    private static final String TAG = "SyncTimeThread";
    private String url;

    public SyncTimeThread(String url) {
        this.url = url;
    }

    @Override
    public Date call() throws Exception {
        Date retval = null;
        NTPUDPClient client = new NTPUDPClient();
        client.setDefaultTimeout(10000);
        try {
            client.open();
            try {
                InetAddress hostAddr = InetAddress.getByName(url);
                TimeInfo info = client.getTime(hostAddr);
                long date = info.getMessage().getTransmitTimeStamp().getTime();
                retval = new Date(date);
            } catch (IOException ioe) {
                Log.e(TAG, ioe.getMessage());
            }
        } catch (SocketException e) {
            Log.e(TAG, e.getMessage());
        }
        return retval;
    }
}
