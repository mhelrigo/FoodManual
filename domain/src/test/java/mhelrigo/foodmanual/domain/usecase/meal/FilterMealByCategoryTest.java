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

import mhelrigo.foodmanual.domain.repository.MealRepository;

@RunWith(MockitoJUnitRunner.class)
public class FilterMealByCategoryTest {
    private static final String FAKE_CATEGORY = "Vegetarian";

    private FilterMealByCategory filterMealByCategory;

    @Mock
    public MealRepository mealRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        filterMealByCategory = new FilterMealByCategory(mealRepository);
    }

    @Test
    public void searchByCategorySuccess() {
        filterMealByCategory.execute(FilterMealByCategory.Params.params(FAKE_CATEGORY));
        verify(mealRepository).searchByCategory(FAKE_CATEGORY);
        verifyNoMoreInteractions(mealRepository);
    }

    @Test
    public void searchByCategoryFailed() {
        expectedException.expect(NullPointerException.class);
        filterMealByCategory.execute(null);
    }
}
