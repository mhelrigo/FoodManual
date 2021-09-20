package mhelrigo.foodmanual.domain.usecase.category;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mhelrigo.foodmanual.domain.repository.CategoryRepository;

@RunWith(MockitoJUnitRunner.class)
public class GetAllIngredientEntityCategoryEntityAreaEntityTest {
    private GetAllCategory getAllCategory;

    @Mock
    CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        getAllCategory = new GetAllCategory(categoryRepository);
    }

    @Test
    public void getAllSuccess() {
        getAllCategory.execute(null);
        verify(categoryRepository).getAll();
        verifyNoMoreInteractions(categoryRepository);
    }
}
