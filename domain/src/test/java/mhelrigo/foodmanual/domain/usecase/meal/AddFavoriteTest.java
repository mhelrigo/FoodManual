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

import mhelrigo.foodmanual.domain.model.meal.Meal;
import mhelrigo.foodmanual.domain.repository.MealRepository;

@RunWith(MockitoJUnitRunner.class)
public class AddFavoriteTest {
    private AddFavorite addFavorite;

    @Mock
    MealRepository mealRepository;

    @Mock
    Meal meal;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        addFavorite = new AddFavorite(mealRepository);
    }

    @Test
    public void addFavoriteSuccess() {
        addFavorite.execute(AddFavorite.Params.params(meal));
        verify(mealRepository).addFavorite(meal);
        verifyNoMoreInteractions(mealRepository);
    }

    @Test
    public void addFavoriteFailed() {
        expectedException.expect(NullPointerException.class);
        addFavorite.execute(null);
    }
}
