package mhelrigo.foodmanual.data.di;

import android.util.Log;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import mhelrigo.foodmanual.data.BuildConfig;
import mhelrigo.foodmanual.data.repository.category.remote.CategoryApi;
import mhelrigo.foodmanual.data.repository.ingredient.remote.IngredientApi;
import mhelrigo.foodmanual.data.repository.meal.remote.MealApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class RemoteModule {
    @Singleton
    @Provides
    static Retrofit retrofit(){
        return new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v2/" + BuildConfig.API_KEY + "/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build();
    }

    private static OkHttpClient getUnsafeOkHttpClient() {

        try {
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, null, new SecureRandom());

            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();

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

    @Singleton
    @Provides
    MealApi mealApi(Retrofit retrofit) {
        return retrofit.create(MealApi.class);
    }

    @Singleton
    @Provides
    CategoryApi categoryApi(Retrofit retrofit) {
        return retrofit.create(CategoryApi.class);
    }

    @Singleton
    @Provides
    IngredientApi ingredientApi(Retrofit retrofit) {
        return retrofit.create(IngredientApi.class);
    }
}
