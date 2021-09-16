package mhelrigo.foodmanual.data.database;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import mhelrigo.foodmanual.data.repository.FoodManualDatabase;
import mhelrigo.foodmanual.data.repository.meal.local.MealDao;
import mhelrigo.foodmanual.data.mapper.MealMapper;
import mhelrigo.foodmanual.domain.model.meal.Meal;

@RunWith(AndroidJUnit4.class)
public class MealDaoTest {
    private MealDao mealDao;
    private FoodManualDatabase foodManualDatabase;

    Meal meal;

    public MealMapper mealMapper;

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        foodManualDatabase = Room.inMemoryDatabaseBuilder(context, FoodManualDatabase.class).build();
        mealDao = foodManualDatabase.mealDao();

        mealMapper = new MealMapper();
        meal = mockMeal();
    }

    @After
    public void packUp() {
        foodManualDatabase.close();
    }

    @Test
    public void addAndVerifyFavorite() {
        mealDao.addFavorite(mealMapper.toDatabase(meal)).test();
        verifyWriteFromDatabaseByBoolean(true);
    }

    @Test
    public void removeAndVerifyFavorite() {
        mealDao.removeFavorite(mealMapper.toDatabase(meal)).test();
        verifyWriteFromDatabaseByBoolean(false);
    }

    private void verifyWriteFromDatabaseByBoolean(Boolean value) {
        mealDao.getAllFavorites()
                .flatMapObservable(meals -> Observable.fromIterable(meals))
                .map(mealMapper::toBusinessData).toList()
                .subscribe(meals -> {
                    assertThat(meals.isEmpty(), not(value));
                });
    }

    public static Meal mockMeal() {
        return new Meal("52771",
                "Spicy Arrabiata Penne",
                null,
                "Vegetarian",
                "Italian",
                "Bring a large pot of water to a boil. Add kosher salt to the boiling water, then add the pasta. Cook according to the package instructions, about 9 minutes.\r\nIn a large skillet over medium-high heat, add the olive oil and heat until the oil starts to shimmer. Add the garlic and cook, stirring, until fragrant, 1 to 2 minutes. Add the chopped tomatoes, red chile flakes, Italian seasoning and salt and pepper to taste. Bring to a boil and cook for 5 minutes. Remove from the heat and add the chopped basil.\r\nDrain the pasta and add it to the sauce. Garnish with Parmigiano-Reggiano flakes and more basil and serve warm.",
                "https://www.themealdb.com/images/media/meals/ustsqw1468250014.jpg",
                "Pasta,Curry",
                "https://www.youtube.com/watch?v=1IszT_guI08",
                "penne rigate",
                "olive oil",
                "garlic",
                "chopped tomatoes",
                "red chile flakes",
                "italian seasoning",
                "basil",
                "Parmigiano-Reggiano",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                null,
                null,
                null,
                null,
                null,
                "1 pound",
                "1/4 cup",
                "3 cloves",
                "1 tin ",
                "1/2 teaspoon",
                "1/2 teaspoon",
                "6 leaves",
                "spinkling",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true);
    }
}
