package mhelrigo.foodmanual.data.repository;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Single;
import mhelrigo.foodmanual.data.repository.meal.MealRepositoryImpl;
import mhelrigo.foodmanual.data.repository.meal.local.MealDao;
import mhelrigo.foodmanual.data.mapper.MealMapper;
import mhelrigo.foodmanual.data.repository.meal.remote.MealApi;
import mhelrigo.foodmanual.domain.model.meal.Meals;
import mhelrigo.foodmanual.domain.repository.MealRepository;

@RunWith(MockitoJUnitRunner.class)
public class MealRepositoryTest {
    private static final String FAKE_MEAL_ID = "52771";
    private static final String FAKE_CATEGORY = "Vegetarian";

    private MealRepository mealRepository;

    @Mock
    public MealApi mealApi;
    @Mock
    public MealDao mealDao;
    @Mock
    public MealMapper mealMapper;

    @Mock
    public Meals meals;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        mealRepository = new MealRepositoryImpl(mealApi, mealDao, mealMapper);
    }

    @Test
    public void getLatest() {
        given(mealApi.getLatest()).willReturn(Single.just(meals));
        mealRepository.getLatest().test();

        verify(mealApi).getLatest();
        verifyNoMoreInteractions(mealApi);
    }

    @Test
    public void getRandomly() {
        given(mealApi.getRandomly()).willReturn(Single.just(meals));
        mealRepository.getRandomly().test();

        verify(mealApi).getRandomly();
        verifyNoMoreInteractions(mealApi);
    }

    @Test
    public void getDetails() {
        given(mealApi.getDetails(FAKE_MEAL_ID)).willReturn(Single.just(meals));
        mealRepository.getDetails(FAKE_MEAL_ID).test();

        verify(mealApi).getDetails(FAKE_MEAL_ID);
        verifyNoMoreInteractions(mealApi);
    }

    @Test
    public void searchByCategory() {
        given(mealApi.searchByCategory(FAKE_CATEGORY)).willReturn(Single.just(meals));
        mealRepository.searchByCategory(FAKE_CATEGORY).test();

        verify(mealApi).searchByCategory(FAKE_CATEGORY);
        verifyNoMoreInteractions(mealApi);
    }
}
