package mhelrigo.foodmanual.domain.usecase.meal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mhelrigo.foodmanual.domain.repository.MealRepository;

@RunWith(MockitoJUnitRunner.class)
public class GetAllFavoritesTest {
    private GetAllFavoriteMeal getAllFavoriteMeal;

    @Mock
    MealRepository mealRepository;

    @Before
    public void setUp() {
        getAllFavoriteMeal = new GetAllFavoriteMeal(mealRepository);
    }

    @Test
    public void getAllFavoritesSuccess() {
        getAllFavoriteMeal.execute(null);
        verify(mealRepository).getAllFavorites();
        verifyNoMoreInteractions(mealRepository);
    }
}
