package mhelrigo.foodmanual.data.repository.remote;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MockRestService {
    public static Retrofit mockRetrofit(MockWebServer mockWebServer) {
        return new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static MockResponse mockResponse() {
        return new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8");
    }
}
