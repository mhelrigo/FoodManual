package mhelrigo.foodmanual.domain.usecase.ingredient;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mhelrigo.foodmanual.domain.repository.IngredientRepository;

@RunWith(MockitoJUnitRunner.class)
public class GetAllIngredientEntityCategoryEntityAreaEntityTest {
    private GetAllIngredient getAllIngredient;

    @Mock
    public IngredientRepository ingredientRepository;

    @Before
    public void setUp() throws Exception {
        getAllIngredient = new GetAllIngredient(ingredientRepository);
    }

    @Test
    public void getAllSuccess() {
        getAllIngredient.execute(null);
        verify(ingredientRepository).getAll();
        verifyNoMoreInteractions(ingredientRepository);
    }
}
