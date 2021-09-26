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
public class FilterByMainIngredientTest {
    private static final String FAKE_INGREDIENT = "Chicken";

    private FilterByMainIngredient filterByMainIngredient;

    @Mock
    MealRepository mealRepository;

    @Before
    public void setUp() throws Exception {
        filterByMainIngredient = new FilterByMainIngredient(mealRepository);
    }

    @Test
    public void filterMealsByMainIngredient() {
        filterByMainIngredient.execute(FilterByMainIngredient.Params.params(FAKE_INGREDIENT));
        verify(mealRepository).filterByMainIngredient(FAKE_INGREDIENT);
        verifyNoMoreInteractions(mealRepository);
    }
}
