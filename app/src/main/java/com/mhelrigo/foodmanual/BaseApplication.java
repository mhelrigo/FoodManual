package com.mhelrigo.foodmanual;

import com.mhelrigo.foodmanual.di.DaggerAppComponent;
import com.mhelrigo.foodmanual.ui.connectionreceiver.ConnectivityReceiver;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    private static BaseApplication mBaseApplication;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

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
