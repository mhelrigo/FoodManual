package com.mhelrigo.foodmanual.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Singleton
    @Provides
    static RequestManager providesGlideInstance(Application application){
        return Glide.with(application);
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
}
