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
public class GetLatestTest {
    private GetLatest getLatest;

    @Mock
    MealRepository mealRepository;

    @Before
    public void setUp() {
        getLatest = new GetLatest(mealRepository);
    }

    @Test
    public void getLatestSuccess() {
        getLatest.execute(null);

        verify(mealRepository).getLatest();
        verifyNoMoreInteractions(mealRepository);
    }
}
