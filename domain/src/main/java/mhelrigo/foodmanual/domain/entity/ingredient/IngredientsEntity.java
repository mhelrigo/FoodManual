package mhelrigo.foodmanual.domain.entity.ingredient;

import java.util.List;

public class IngredientsEntity {
    private List<IngredientEntity> meals;

    public IngredientsEntity(List<IngredientEntity> meals) {
        this.meals = meals;
    }

    public List<IngredientEntity> getMeals() {
        return meals;
    }

    public void setMeals(List<IngredientEntity> meals) {
        this.meals = meals;
    }
}
