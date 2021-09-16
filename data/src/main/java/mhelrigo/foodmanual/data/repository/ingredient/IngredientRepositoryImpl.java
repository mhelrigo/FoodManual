package mhelrigo.foodmanual.data.repository.ingredient;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import mhelrigo.foodmanual.data.repository.ingredient.remote.IngredientApi;
import mhelrigo.foodmanual.domain.model.ingredient.Ingredients;
import mhelrigo.foodmanual.domain.repository.IngredientRepository;

@Singleton
public class IngredientRepositoryImpl implements IngredientRepository {
    private IngredientApi ingredientApi;

    @Inject
    public IngredientRepositoryImpl(IngredientApi ingredientApi) {
        this.ingredientApi = ingredientApi;
    }

    @Override
    public Single<Ingredients> getAll() {
        return ingredientApi.getAll();
    }
}
