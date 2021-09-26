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
public class GetMealDetailsTest {
    private static final String FAKE_MEAL_ID = "52771";

    private GetMealDetails getMealDetails;

    @Mock
    private MealRepository mealRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        getMealDetails = new GetMealDetails(mealRepository);
    }

    @Test
    public void getDetailsSuccess() {
        getMealDetails.execute(GetMealDetails.Params.params(FAKE_MEAL_ID));
        verify(mealRepository).getDetails(FAKE_MEAL_ID);
        verifyNoMoreInteractions(mealRepository);
    }

    @Test
    public void getDetailsFailed() {
        expectedException.expect(NullPointerException.class);
        getMealDetails.execute(null);
    }
}
