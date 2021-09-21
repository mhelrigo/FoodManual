package mhelrigo.foodmanual.data.mapper;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mhelrigo.foodmanual.data.entity.meal.MealDatabaseEntity;
import mhelrigo.foodmanual.data.entity.meal.MealsApiEntity;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;
import mhelrigo.foodmanual.domain.entity.meal.MealsEntity;

@RunWith(MockitoJUnitRunner.class)
public class MealMapperTest {
    private MealMapper mealMapper;

    @Mock
    MealEntity mealEntity;

    @Mock
    MealDatabaseEntity mealDatabaseEntity;

    @Mock
    MealsApiEntity mealsApiEntity;

    @Before
    public void setUp() throws Exception {
        mealMapper = new MealMapper();
    }

    @Test
    public void transformDomainEntityToDatabaseEntity() {
        assertThat(mealMapper.transform(mealEntity), is(instanceOf(MealDatabaseEntity.class)));
    }

    @Test
    public void transformDatabaseEntityToDomainEntity() {
        assertThat(mealMapper.transform(mealDatabaseEntity), is(instanceOf(MealEntity.class)));
    }

    @Test
    public void transformApiResponseToDomainEntity() {
        assertThat(mealMapper.transform(mealsApiEntity), is(instanceOf(MealsEntity.class)));
    }
}
