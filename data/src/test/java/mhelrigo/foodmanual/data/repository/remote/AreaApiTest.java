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
import mhelrigo.foodmanual.data.repository.area.remote.AreaApi;
import mhelrigo.foodmanual.domain.model.area.Areas;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class AreaApiTest {
    private MockWebServer mockWebServer;
    private MockResponse mockResponse;

    private AreaApi areaApi;

    private Gson gson;

    public static final String FAKE_AREA = "{\"meals\":[{\"strArea\":\"American\"},{\"strArea\":\"British\"},{\"strArea\":\"Canadian\"},{\"strArea\":\"Chinese\"},{\"strArea\":\"Croatian\"},{\"strArea\":\"Dutch\"},{\"strArea\":\"Egyptian\"},{\"strArea\":\"French\"},{\"strArea\":\"Greek\"},{\"strArea\":\"Indian\"},{\"strArea\":\"Irish\"},{\"strArea\":\"Italian\"},{\"strArea\":\"Jamaican\"},{\"strArea\":\"Japanese\"},{\"strArea\":\"Kenyan\"},{\"strArea\":\"Malaysian\"},{\"strArea\":\"Mexican\"},{\"strArea\":\"Moroccan\"},{\"strArea\":\"Polish\"},{\"strArea\":\"Portuguese\"},{\"strArea\":\"Russian\"},{\"strArea\":\"Spanish\"},{\"strArea\":\"Thai\"},{\"strArea\":\"Tunisian\"},{\"strArea\":\"Turkish\"},{\"strArea\":\"Unknown\"},{\"strArea\":\"Vietnamese\"}]}";

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();

        areaApi = MockRestService.mockRetrofit(mockWebServer).create(AreaApi.class);

        gson = new Gson();

        mockResponse = MockRestService.mockResponse()
                .setResponseCode(200)
                .setBody(FAKE_AREA);

        mockWebServer.enqueue(mockResponse);

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }

    @Test
    public void getAll() {
        Areas mocked = gson.fromJson(FAKE_AREA, Areas.class);
        Areas actual = areaApi.getAll()
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
