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
public class GetLatestMealsEntityTest {
    private GetLatestMeals getLatestMeals;

    @Mock
    MealRepository mealRepository;

    @Before
    public void setUp() {
        getLatestMeals = new GetLatestMeals(mealRepository);
    }

    @Test
    public void getLatestSuccess() {
        getLatestMeals.execute(null);

        verify(mealRepository).getLatest();
        verifyNoMoreInteractions(mealRepository);
    }
}
