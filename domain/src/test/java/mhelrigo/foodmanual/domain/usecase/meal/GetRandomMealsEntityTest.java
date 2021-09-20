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
public class GetRandomMealsEntityTest {
    private GetRandomMeals getRandomMeals;

    @Mock
    MealRepository mealRepository;

    @Before
    public void setUp() {
        getRandomMeals = new GetRandomMeals(mealRepository);
    }

    @Test
    public void getRandomlySuccess() {
        getRandomMeals.execute(null);

        verify(mealRepository).getRandomly();
        verifyNoMoreInteractions(mealRepository);
    }
}
