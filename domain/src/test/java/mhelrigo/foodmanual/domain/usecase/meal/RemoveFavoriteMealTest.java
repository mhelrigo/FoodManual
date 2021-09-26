package mhelrigo.foodmanual.domain.usecase.meal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mhelrigo.foodmanual.domain.entity.meal.MealEntity;
import mhelrigo.foodmanual.domain.repository.MealRepository;

@RunWith(MockitoJUnitRunner.class)
public class RemoveFavoriteMealTest {
    private static final String FAKE_MEAL_ID = "52771";

    private RemoveFavoriteMeal removeFavoriteMeal;

    @Mock
    public MealRepository mealRepository;

    @Mock
    MealEntity mealEntity;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        removeFavoriteMeal = new RemoveFavoriteMeal(mealRepository);
    }

    @Test
    public void removeFavoriteSuccess() {
        removeFavoriteMeal.execute(RemoveFavoriteMeal.Params.params(mealEntity));
        verify(mealRepository).removeFavorite(mealEntity);
        verifyNoMoreInteractions(mealRepository);
    }

    @Test
    public void removeFavoriteFailed() {
        expectedException.expect(NullPointerException.class);
        removeFavoriteMeal.execute(null);
    }
}
