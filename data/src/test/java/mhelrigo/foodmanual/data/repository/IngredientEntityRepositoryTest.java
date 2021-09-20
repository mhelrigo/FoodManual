package mhelrigo.foodmanual.data.repository;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Single;
import mhelrigo.foodmanual.data.repository.ingredient.IngredientRepositoryImpl;
import mhelrigo.foodmanual.data.repository.ingredient.remote.IngredientApi;
import mhelrigo.foodmanual.domain.entity.ingredient.IngredientsEntity;
import mhelrigo.foodmanual.domain.repository.IngredientRepository;

@RunWith(MockitoJUnitRunner.class)
public class IngredientEntityRepositoryTest {
    private IngredientRepository ingredientRepository;

    @Mock
    IngredientApi ingredientApi;

    @Mock
    IngredientsEntity ingredientsEntity;

    @Before
    public void setUp() throws Exception {
        ingredientRepository = new IngredientRepositoryImpl(ingredientApi);
    }

    @Test
    public void getAll() {
        given(ingredientApi.getAll()).willReturn(Single.just(ingredientsEntity));
        ingredientRepository.getAll().test();

        verify(ingredientApi).getAll();
        verifyNoMoreInteractions(ingredientApi);
    }
}
