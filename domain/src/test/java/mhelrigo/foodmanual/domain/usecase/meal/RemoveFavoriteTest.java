package mhelrigo.foodmanual.domain.usecase.meal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static mhelrigo.foodmanual.domain.FoodTestModel.FAKE_MEAL_ID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mhelrigo.foodmanual.domain.FoodTestModel;
import mhelrigo.foodmanual.domain.model.meal.Meal;
import mhelrigo.foodmanual.domain.repository.MealRepository;

@RunWith(MockitoJUnitRunner.class)
public class RemoveFavoriteTest {
    private RemoveFavorite removeFavorite;

    @Mock
    public MealRepository mealRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        removeFavorite = new RemoveFavorite(mealRepository);
    }

    @Test
    public void removeFavoriteSuccess() {
        Meal meal = FoodTestModel.mockMeal(FAKE_MEAL_ID);
        removeFavorite.execute(RemoveFavorite.Params.params(meal));
        verify(mealRepository).removeFavorite(meal);
        verifyNoMoreInteractions(mealRepository);
    }

    @Test
    public void removeFavoriteFailed() {
        expectedException.expect(NullPointerException.class);
        removeFavorite.execute(null);
    }
}
