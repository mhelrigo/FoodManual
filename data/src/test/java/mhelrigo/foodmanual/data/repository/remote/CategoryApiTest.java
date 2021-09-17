package mhelrigo.foodmanual.data.repository.remote;

import static junit.framework.TestCase.assertEquals;

import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mhelrigo.foodmanual.data.repository.category.remote.CategoryApi;
import mhelrigo.foodmanual.domain.model.category.Categories;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class CategoryApiTest {
    private MockWebServer mockWebServer;
    private MockResponse mockResponse;

    private CategoryApi categoryApi;

    private Gson gson;

    public static final String FAKE_CATEGORIES = "{\"meals\":[{\"strCategory\":\"Beef\"},{\"strCategory\":\"Breakfast\"},{\"strCategory\":\"Chicken\"},{\"strCategory\":\"Dessert\"},{\"strCategory\":\"Goat\"},{\"strCategory\":\"Lamb\"},{\"strCategory\":\"Miscellaneous\"},{\"strCategory\":\"Pasta\"},{\"strCategory\":\"Pork\"},{\"strCategory\":\"Seafood\"},{\"strCategory\":\"Side\"},{\"strCategory\":\"Starter\"},{\"strCategory\":\"Vegan\"},{\"strCategory\":\"Vegetarian\"}]}";

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();

        categoryApi = MockRestService.mockRetrofit(mockWebServer).create(CategoryApi.class);

        gson = new Gson();

        mockResponse = MockRestService.mockResponse()
                .setResponseCode(200)
                .setBody(FAKE_CATEGORIES);
        mockWebServer.enqueue(mockResponse);

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }

    @Test
    public void getAll() {
        Categories mocked = gson.fromJson(FAKE_CATEGORIES, Categories.class);
        Categories actual = categoryApi.getAll()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .blockingGet();

        assertEquals(actual.getMeals().size(), mocked.getMeals().size());
    }

    @After
    public void packUp() throws IOException {
        mockWebServer.shutdown();
    }
}
