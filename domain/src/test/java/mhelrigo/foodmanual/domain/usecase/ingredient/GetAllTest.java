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
public class GetAllTest {
    private GetAll getAll;

    @Mock
    public IngredientRepository ingredientRepository;

    @Before
    public void setUp() throws Exception {
        getAll = new GetAll(ingredientRepository);
    }

    @Test
    public void getAllSuccess() {
        getAll.execute(null);
        verify(ingredientRepository).getAll();
        verifyNoMoreInteractions(ingredientRepository);
    }
}
