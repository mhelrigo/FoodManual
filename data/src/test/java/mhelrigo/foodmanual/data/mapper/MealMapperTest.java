package mhelrigo.foodmanual.data.mapper;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mhelrigo.foodmanual.domain.model.meal.Meal;

@RunWith(MockitoJUnitRunner.class)
public class MealMapperTest {
    private MealMapper mealMapper;

    @Mock
    Meal mealDomain;

    @Mock
    mhelrigo.foodmanual.data.model.Meal mealData;

    @Before
    public void setUp() throws Exception {
        mealMapper = new MealMapper();
    }

    @Test
    public void toData() {
        assertThat(mealMapper.toDatabase(mealDomain), is(instanceOf(mhelrigo.foodmanual.data.model.Meal.class)));
    }

    @Test
    public void toDomain() {
        assertThat(mealMapper.toBusinessData(mealData), is(Meal.class));
    }
}
