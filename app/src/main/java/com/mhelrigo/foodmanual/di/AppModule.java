package com.mhelrigo.foodmanual.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mhelrigo.foodmanual.R;

import javax.inject.Named;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    public static final String IS_TABLET = "IS_TABLET";

    @Singleton
    @Provides
    static SharedPreferences sharedPreferences(Application application){
        return application.getSharedPreferences("com.mhelrigo.foodmanual.sharedPreferences", Context.MODE_PRIVATE);
    }

    @Provides
    @Named(IS_TABLET)
    Boolean isTablet(@ApplicationContext Context context) {
        return context.getResources().getBoolean(R.bool.isTablet);
    }
}
