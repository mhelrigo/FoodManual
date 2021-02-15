package com.mhelrigo.foodmanual.data.remote;

import android.util.Log;

import com.mhelrigo.foodmanual.utils.RetrofitErrorUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RetrofitRetryInterceptor implements Interceptor {
    private static final String TAG = "RetrofitRetryIntercepto";
    private static final int RETRY_LIMIT = 50000;
    private static final int WAIT_THRESHOLD = 10000;

    private Response response = null;
    private int retryCount = 0;

    public RetrofitRetryInterceptor() {
    }

    @Override
    public Response intercept(Interceptor.Chain chain) {
        Request request = chain.request();
        response =  sendRequest(chain,request);
        while (response == null && retryCount < RETRY_LIMIT) {
            Log.e("intercept", "Request failed - " + retryCount);
            retryCount++;
            try {
                Thread.sleep(WAIT_THRESHOLD);
            } catch (InterruptedException e) {
                response = sendRequest(chain,request);
            }
            response = sendRequest(chain,request);
        }
        return response;
    }

    private Response sendRequest(Chain chain, Request request){
        try {
            response = chain.proceed(request);
            if(!response.isSuccessful()) {
                return null;
            }
            else {
                return response;
            }
        } catch (IOException e) {
            Log.e(TAG, "Returning Request Error...", e);
            if (e instanceof SocketTimeoutException){
                Log.e(TAG, "Returning SocketTimeoutException...", e);
                return null;
            }
            return RetrofitErrorUtils.ioError(request);
        }
    }
}
