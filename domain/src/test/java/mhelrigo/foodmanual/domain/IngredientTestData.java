package mhelrigo.foodmanual.domain;

import mhelrigo.foodmanual.domain.model.ingredient.Ingredient;
import mhelrigo.foodmanual.domain.model.ingredient.Ingredients;

public class IngredientTestData {
    public Ingredients ingredients;

    public IngredientTestData() {
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.getMeals().add(ingredient);
    }

    public static Ingredient mockIngredient(String id) {
        return new Ingredient(id, "Chicken", "The chicken is a type of…annals of Thutmose III.", null);
    }
}
