package com.mhelrigo.foodmanual;

import android.app.Application;

import com.mhelrigo.foodmanual.ui.connectionreceiver.ConnectivityReceiver;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class BaseApplication extends Application {

    private static BaseApplication mBaseApplication;

    /*@Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
*/
    @Override
    public void onCreate() {
        super.onCreate();

        mBaseApplication = this;
    }

    public static synchronized BaseApplication getInstance(){
        return mBaseApplication;
    }

    public void setConnectionListener(ConnectivityReceiver.ConnectivityReceiverListener listener){
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
