package com.mhelrigo.foodmanual.model.ingredient;

import java.util.List;

public class IngredientsModel {
    private List<IngredientModel> ingredientModels;

    public IngredientsModel(List<IngredientModel> ingredientModels) {
        this.ingredientModels = ingredientModels;
    }

    public List<IngredientModel> getMeals() {
        return ingredientModels;
    }

    public void setMeals(List<IngredientModel> meals) {
        this.ingredientModels = meals;
    }
}
