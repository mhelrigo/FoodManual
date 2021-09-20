package mhelrigo.foodmanual.domain.repository;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.entity.ingredient.IngredientsEntity;

public interface IngredientRepository {
    Single<IngredientsEntity> getAll();
}
