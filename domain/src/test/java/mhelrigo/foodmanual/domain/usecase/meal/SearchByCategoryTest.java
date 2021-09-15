package mhelrigo.foodmanual.domain.usecase.meal;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mhelrigo.foodmanual.domain.repository.MealRepository;

@RunWith(MockitoJUnitRunner.class)
public class SearchByCategoryTest {
    public static final String FAKE_CATEGORY = "Some Category";

    private SearchByCategory searchByCategory;

    @Mock
    public MealRepository mealRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        searchByCategory = new SearchByCategory(mealRepository);
    }

    @Test
    public void searchByCategorySuccess() {
        searchByCategory.execute(SearchByCategory.Params.params(FAKE_CATEGORY));
        verify(mealRepository).searchByCategory(FAKE_CATEGORY);
        verifyNoMoreInteractions(mealRepository);
    }

    @Test
    public void searchByCategoryFailed() {
        expectedException.expect(NullPointerException.class);
        searchByCategory.execute(null);
    }
}
