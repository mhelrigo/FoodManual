package mhelrigo.foodmanual.domain.entity.ingredient;

import java.util.List;

public class IngredientsEntity {
    private List<IngredientEntity> ingredientEntity;

    public IngredientsEntity(List<IngredientEntity> ingredientEntity) {
        this.ingredientEntity = ingredientEntity;
    }

    public List<IngredientEntity> getIngredientEntity() {
        return ingredientEntity;
    }

    public void setIngredientEntity(List<IngredientEntity> ingredientEntity) {
        this.ingredientEntity = ingredientEntity;
    }
}
