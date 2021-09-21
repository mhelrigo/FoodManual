package mhelrigo.foodmanual.data.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import mhelrigo.foodmanual.data.entity.ingredient.IngredientApiEntity;
import mhelrigo.foodmanual.domain.entity.ingredient.IngredientsEntity;

@Singleton
public class IngredientMapper {
    @Inject
    public IngredientMapper() {
    }

    public IngredientsEntity transform(IngredientApiEntity ingredientApiEntity) {
        return new IngredientsEntity(ingredientApiEntity.getIngredientEntity());
    }
}
