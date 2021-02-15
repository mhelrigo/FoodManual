package com.mhelrigo.foodmanual;

import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    private static final String TAG = "BaseViewModel";

    protected boolean isRetryNetworkRequest = false;
    protected ObservableBoolean isNetworkConnected = new ObservableBoolean(true);

    public void setIsNetworkConnected(boolean isNetworkConnected) {
        this.isNetworkConnected.set(isNetworkConnected);
    }

    public ObservableBoolean getIsNetworkConnected() {
        return isNetworkConnected;
    }

    protected void onRetryNetworkRequests() {
        Log.e(TAG, "Retrying...");
        isRetryNetworkRequest = false;
    }
}
