package mhelrigo.foodmanual.data.mapper;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mhelrigo.foodmanual.data.entity.category.CategoriesApiEntity;
import mhelrigo.foodmanual.domain.entity.category.CategoriesEntity;

@RunWith(MockitoJUnitRunner.class)
public class CategoryMapperTest {
    private CategoryMapper categoryMapper;

    @Mock
    CategoriesApiEntity categoriesApiEntity;

    @Before
    public void setUp() throws Exception {
        categoryMapper = new CategoryMapper();
    }

    @Test
    public void transformApiResponseToDomainEntity() {
        assertThat(categoryMapper.transform(categoriesApiEntity), is(instanceOf(CategoriesEntity.class)));
    }
}
