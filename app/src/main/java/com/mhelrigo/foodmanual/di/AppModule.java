package com.mhelrigo.foodmanual.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mhelrigo.foodmanual.MealViewModel;
import com.mhelrigo.foodmanual.data.local.AppDatabase;
import com.mhelrigo.foodmanual.data.remote.RetrofitRetryInterceptor;
import com.mhelrigo.foodmanual.utils.Constants;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class AppModule {
    @Binds
    @IntoMap
    @ViewModelKey(MealViewModel.class)
    public abstract ViewModel mealViewModel(MealViewModel mealViewModel);

    @Singleton
    @Provides
    static RequestManager providesGlideInstance(Application application){
        return Glide.with(application);
    }

    @Singleton
    @Provides
    static Retrofit retrofit(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build();
    }

    @Singleton
    @Provides
    static AppDatabase appDatabase(Application application){
        return Room.databaseBuilder(application, AppDatabase.class, "fm")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    static SharedPreferences sharedPreferences(Application application){
        return application.getSharedPreferences("com.mhelrigo.foodmanual.di", Context.MODE_PRIVATE);
    }

    @Provides
    static FirebaseAnalytics firebaseAnalytics(Application application){
        return FirebaseAnalytics.getInstance(application);
    }

    private static OkHttpClient getUnsafeOkHttpClient() {

        try {
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, null, new SecureRandom());

            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.addInterceptor(new RetrofitRetryInterceptor());

            builder.readTimeout(5, TimeUnit.SECONDS);
            builder.connectTimeout(5, TimeUnit.SECONDS);
            builder.writeTimeout(5, TimeUnit.SECONDS);

            builder.sslSocketFactory(sslSocketFactory, new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            });
            builder.hostnameVerifier((hostname, session) -> true);

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            builder.addInterceptor(interceptor);

            OkHttpClient okHttpClient = builder.build();

            return okHttpClient;
        }catch (Exception e){
            Log.e("Exception okHttpClient", e.getMessage());
            return null;
        }
    }
}
