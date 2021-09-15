package mhelrigo.foodmanual.domain.usecase.meal;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import static mhelrigo.foodmanual.domain.FoodTestModel.FAKE_MEAL_ID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.FoodTestModel;
import mhelrigo.foodmanual.domain.repository.MealRepository;

@RunWith(MockitoJUnitRunner.class)
public class GetDetailsTest {
    private GetDetails getDetails;

    @Mock
    private MealRepository mealRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        getDetails = new GetDetails(mealRepository);
    }

    @Test
    public void getDetailsSuccess() {
        getDetails.execute(GetDetails.Params.params(FAKE_MEAL_ID));
        verify(mealRepository).getDetails(FAKE_MEAL_ID);
        verifyNoMoreInteractions(mealRepository);
    }

    @Test
    public void getDetailsFailed() {
        expectedException.expect(NullPointerException.class);
        getDetails.execute(null);
    }
}
