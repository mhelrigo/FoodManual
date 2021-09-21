package mhelrigo.foodmanual.data.entity.ingredient;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import mhelrigo.foodmanual.domain.entity.ingredient.IngredientEntity;

public class IngredientApiEntity {
    @SerializedName("meals")
    private List<IngredientEntity> ingredientEntity;

    public IngredientApiEntity(List<IngredientEntity> ingredientEntity) {
        this.ingredientEntity = ingredientEntity;
    }

    public List<IngredientEntity> getIngredientEntity() {
        return ingredientEntity;
    }

    public void setIngredientEntity(List<IngredientEntity> ingredientEntity) {
        this.ingredientEntity = ingredientEntity;
    }
}
