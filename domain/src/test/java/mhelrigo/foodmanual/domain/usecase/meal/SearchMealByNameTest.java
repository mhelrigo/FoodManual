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
public class SearchMealByNameTest {
    private static final String FAKE_NAME = "Kare-kare";

    private SearchMealByName searchMealByName;

    @Mock
    MealRepository mealRepository;

    @Before
    public void setUp() throws Exception {
        searchMealByName = new SearchMealByName(mealRepository);
    }

    @Test
    public void searchMealByName() {
        searchMealByName.execute(SearchMealByName.Params.params(FAKE_NAME));
        verify(mealRepository).searchByName(FAKE_NAME);
        verifyNoMoreInteractions(mealRepository);
    }
}
