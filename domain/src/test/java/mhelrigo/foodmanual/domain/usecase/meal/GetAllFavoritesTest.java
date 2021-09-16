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
    private GetAllFavorites getAllFavorites;

    @Mock
    MealRepository mealRepository;

    @Before
    public void setUp() {
        getAllFavorites = new GetAllFavorites(mealRepository);
    }

    @Test
    public void getAllFavoritesSuccess() {
        getAllFavorites.execute(null);
        verify(mealRepository).getAllFavorites();
        verifyNoMoreInteractions(mealRepository);
    }
}
