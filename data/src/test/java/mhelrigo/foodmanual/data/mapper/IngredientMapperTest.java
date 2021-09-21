package mhelrigo.foodmanual.data.mapper;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mhelrigo.foodmanual.data.entity.ingredient.IngredientApiEntity;
import mhelrigo.foodmanual.domain.entity.ingredient.IngredientsEntity;

@RunWith(MockitoJUnitRunner.class)
public class IngredientMapperTest {
    private IngredientMapper ingredientMapper;

    @Mock
    IngredientApiEntity ingredientApiEntity;

    @Before
    public void setUp() throws Exception {
        ingredientMapper = new IngredientMapper();
    }

    @Test
    public void transformApiResponseToDomainEntity() {
        assertThat(ingredientMapper.transform(ingredientApiEntity), is(instanceOf(IngredientsEntity.class)));
    }
}
