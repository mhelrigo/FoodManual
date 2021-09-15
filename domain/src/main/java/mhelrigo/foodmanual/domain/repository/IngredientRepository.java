package mhelrigo.foodmanual.domain.repository;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.ingredient.Ingredients;

public interface IngredientRepository {
    Single<Ingredients> getAll();
}
