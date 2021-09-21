package mhelrigo.foodmanual.data.repository.ingredient;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import mhelrigo.foodmanual.data.mapper.IngredientMapper;
import mhelrigo.foodmanual.data.repository.ingredient.remote.IngredientApi;
import mhelrigo.foodmanual.domain.entity.ingredient.IngredientsEntity;
import mhelrigo.foodmanual.domain.repository.IngredientRepository;

@Singleton
public class IngredientRepositoryImpl implements IngredientRepository {
    private IngredientApi ingredientApi;
    private IngredientMapper ingredientMapper;

    @Inject
    public IngredientRepositoryImpl(IngredientApi ingredientApi, IngredientMapper ingredientMapper) {
        this.ingredientApi = ingredientApi;
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public Single<IngredientsEntity> getAll() {
        return ingredientApi.getAll().map(ingredientApiEntity -> ingredientMapper.transform(ingredientApiEntity));
    }
}
