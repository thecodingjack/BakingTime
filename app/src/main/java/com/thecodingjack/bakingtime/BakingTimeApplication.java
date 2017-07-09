package com.thecodingjack.bakingtime;

import android.app.Application;

/**
 * Created by lamkeong on 7/9/2017.
 */

public class BakingTimeApplication extends Application {
    private static BakingTimeApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized BakingTimeApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
