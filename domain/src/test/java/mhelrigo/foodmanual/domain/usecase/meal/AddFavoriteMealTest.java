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
public class AddFavoriteMealTest {
    private AddFavoriteMeal addFavoriteMeal;

    @Mock
    MealRepository mealRepository;

    @Mock
    MealEntity mealEntity;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        addFavoriteMeal = new AddFavoriteMeal(mealRepository);
    }

    @Test
    public void addFavoriteSuccess() {
        addFavoriteMeal.execute(AddFavoriteMeal.Params.params(mealEntity));
        verify(mealRepository).addFavorite(mealEntity);
        verifyNoMoreInteractions(mealRepository);
    }

    @Test
    public void addFavoriteFailed() {
        expectedException.expect(NullPointerException.class);
        addFavoriteMeal.execute(null);
    }
}
