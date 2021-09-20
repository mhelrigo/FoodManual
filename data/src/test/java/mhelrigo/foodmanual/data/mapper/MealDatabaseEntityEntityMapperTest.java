package mhelrigo.foodmanual.data.mapper;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mhelrigo.foodmanual.data.entity.MealDatabaseEntity;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;

@RunWith(MockitoJUnitRunner.class)
public class MealDatabaseEntityEntityMapperTest {
    private MealDatabaseMapper mealDatabaseMapper;

    @Mock
    MealEntity mealEntityDomain;

    @Mock
    MealDatabaseEntity mealDatabaseEntityData;

    @Before
    public void setUp() throws Exception {
        mealDatabaseMapper = new MealDatabaseMapper();
    }

    @Test
    public void toData() {
        assertThat(mealDatabaseMapper.transform(mealEntityDomain), is(instanceOf(MealDatabaseEntity.class)));
    }

    @Test
    public void toDomain() {
        assertThat(mealDatabaseMapper.transform(mealDatabaseEntityData), is(MealEntity.class));
    }
}
