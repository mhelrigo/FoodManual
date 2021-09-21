package mhelrigo.foodmanual.data.repository.remote;

import static junit.framework.TestCase.assertEquals;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mhelrigo.foodmanual.data.entity.ingredient.IngredientApiEntity;
import mhelrigo.foodmanual.data.repository.ingredient.remote.IngredientApi;
import mhelrigo.foodmanual.domain.entity.ingredient.IngredientsEntity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class IngredientEntityApiTest {
    private MockWebServer mockWebServer;
    private MockResponse mockResponse;

    private IngredientApi ingredientApi;

    private Gson gson;

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();

        ingredientApi = MockRestService.mockRetrofit(mockWebServer).create(IngredientApi.class);

        gson = new Gson();

        mockResponse = MockRestService.mockResponse()
                .setResponseCode(200)
                .setBody(FAKE_INGREDIENTS);
        mockWebServer.enqueue(mockResponse);

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
    }

    @Test
    public void getAll() {
        IngredientApiEntity mocked = gson.fromJson(FAKE_INGREDIENTS, IngredientApiEntity.class);
        IngredientApiEntity actual = ingredientApi.getAll()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .blockingGet();

        assertEquals(actual.getIngredientEntity().size(), mocked.getIngredientEntity().size());
    }

    public static final String FAKE_INGREDIENTS = "{\"meals\":[{\"idIngredient\":\"1\",\"strIngredient\":\"Chicken\",\"strDescription\":\"The chicken is a type of domesticated fowl, a subspecies of the red junglefowl (Gallus gallus). It is one of the most common and widespread domestic animals, with a total population of more than 19 billion as of 2011. There are more chickens in the world than any other bird or domesticated fowl. Humans keep chickens primarily as a source of food (consuming both their meat and eggs) and, less commonly, as pets. Originally raised for cockfighting or for special ceremonies, chickens were not kept for food until the Hellenistic period (4th\\u20132nd centuries BC).\\r\\n\\r\\nGenetic studies have pointed to multiple maternal origins in South Asia, Southeast Asia, and East Asia, but with the clade found in the Americas, Europe, the Middle East and Africa originating in the Indian subcontinent. From ancient India, the domesticated chicken spread to Lydia in western Asia Minor, and to Greece by the 5th century BC. Fowl had been known in Egypt since the mid-15th century BC, with the \\\"bird that gives birth every day\\\" having come to Egypt from the land between Syria and Shinar, Babylonia, according to the annals of Thutmose III.\",\"strType\":null},{\"idIngredient\":\"2\",\"strIngredient\":\"Salmon\",\"strDescription\":\"Salmon is the common name for several species of ray-finned fish in the family Salmonidae. Other fish in the same family include trout, char, grayling and whitefish. Salmon are native to tributaries of the North Atlantic (genus Salmo) and Pacific Ocean (genus Oncorhynchus). Many species of salmon have been introduced into non-native environments such as the Great Lakes of North America and Patagonia in South America. Salmon are intensively farmed in many parts of the world.\\r\\n\\r\\nTypically, salmon are anadromous: they hatch in fresh water, migrate to the ocean, then return to fresh water to reproduce. However, populations of several species are restricted to fresh water through their lives. Folklore has it that the fish return to the exact spot where they hatched to spawn. Tracking studies have shown this to be mostly true. A portion of a returning salmon run may stray and spawn in different freshwater systems; the percent of straying depends on the species of salmon. Homing behavior has been shown to depend on olfactory memory. Salmon date back to the Neogene.\",\"strType\":null},{\"idIngredient\":\"3\",\"strIngredient\":\"Beef\",\"strDescription\":\"Beef is the culinary name for meat from cattle, particularly skeletal muscle. Humans have been eating beef since prehistoric times. Beef is a source of high-quality protein and nutrients.\\r\\n\\r\\nMost beef skeletal muscle meat can be used as is by merely cutting into certain parts, such as roasts, short ribs or steak (filet mignon, sirloin steak, rump steak, rib steak, rib eye steak, hanger steak, etc.), while other cuts are processed (corned beef or beef jerky). Trimmings, on the other hand, are usually mixed with meat from older, leaner (therefore tougher) cattle, are ground, minced or used in sausages. The blood is used in some varieties called blood sausage. Other parts that are eaten include other muscles and offal, such as the oxtail, liver, tongue, tripe from the reticulum or rumen, glands (particularly the pancreas and thymus, referred to as sweetbread), the heart, the brain (although forbidden where there is a danger of bovine spongiform encephalopathy, BSE, commonly referred to as mad cow disease), the kidneys, and the tender testicles of the bull (known in the United States as calf fries, prairie oysters, or Rocky Mountain oysters). Some intestines are cooked and eaten as is, but are more often cleaned and used as natural sausage casings. The bones are used for making beef stock.\",\"strType\":null},{\"idIngredient\":\"4\",\"strIngredient\":\"Pork\",\"strDescription\":\"Pork is the culinary name for the flesh of a domestic pig (Sus scrofa domesticus). It is the most commonly consumed meat worldwide,[1] with evidence of pig husbandry dating back to 5000 BC.\\r\\n\\r\\nPork is eaten both freshly cooked and preserved. Curing extends the shelf life of the pork products. Ham, smoked pork, gammon, bacon and sausage are examples of preserved pork. Charcuterie is the branch of cooking devoted to prepared meat products, many from pork.\\r\\n\\r\\nPig is the most popular meat in the Eastern and non-Muslim parts of Southeastern Asia (Indochina, Philippines, Singapore, East Timor) and is also very common in the Western world, especially in Central Europe. It is highly prized in Asian cuisines for its fat content and pleasant texture. Consumption of pork is forbidden by Jewish, Muslim and Rastafarian dietary law, for religious reasons, with several suggested possible causes.\",\"strType\":null},{\"idIngredient\":\"5\",\"strIngredient\":\"Avocado\",\"strDescription\":\"The avocado, a tree with probable origin in South Central Mexico, is classified as a member of the flowering plant family Lauraceae. The fruit of the plant, also called an avocado (or avocado pear or alligator pear), is botanically a large berry containing a single large seed.\\r\\n\\r\\nAvocados are commercially valuable and are cultivated in tropical and Mediterranean climates throughout the world. They have a green-skinned, fleshy body that may be pear-shaped, egg-shaped, or spherical. Commercially, they ripen after harvesting. Avocado trees are partially self-pollinating, and are often propagated through grafting to maintain predictable fruit quality and quantity. In 2017, Mexico produced 34% of the world supply of avocados.\",\"strType\":null},{\"idIngredient\":\"9\",\"strIngredient\":\"Apple Cider Vinegar\",\"strDescription\":\"Apple cider vinegar, or cider vinegar, is a vinegar made from fermented apple juice, and used in salad dressings, marinades, vinaigrettes, food preservatives, and chutneys. It is made by crushing apples, then squeezing out the juice. Bacteria and yeast are added to the liquid to start the alcoholic fermentation process, which converts the sugars to alcohol. In a second fermentation step, the alcohol is converted into vinegar by acetic acid-forming bacteria (Acetobacter species). Acetic acid and malic acid combine to give vinegar its sour taste. Apple cider vinegar has no medicinal or nutritional value. There is no high-quality clinical evidence that regular consumption of apple cider vinegar helps to maintain or lose body weight, or is effective to manage blood glucose and lipid levels.\",\"strType\":null},{\"idIngredient\":\"10\",\"strIngredient\":\"Asparagus\",\"strDescription\":\"Asparagus, or garden asparagus, folk name sparrow grass, scientific name Asparagus officinalis, is a perennial flowering plant species in the genus Asparagus. Its young shoots are used as a spring vegetable.\\r\\n\\r\\nIt was once classified in the lily family, like the related Allium species, onions and garlic. However, genetic research places lilies, Allium, and asparagus in three separate families\\u2014the Liliaceae, Amaryllidaceae, and Asparagaceae, respectively\\u2014with the Amaryllidaceae and Asparagaceae being grouped together in the order Asparagales. Sources differ as to the native range of Asparagus officinalis, but generally include most of Europe and western temperate Asia. It is widely cultivated as a vegetable crop.\",\"strType\":null}]}";
}
