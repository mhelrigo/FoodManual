package mhelrigo.foodmanual.domain.model.ingredient;

import java.util.List;

public class Ingredients {
    private List<Ingredient> meals;

    public Ingredients(List<Ingredient> meals) {
        this.meals = meals;
    }

    public List<Ingredient> getMeals() {
        return meals;
    }

    public void setMeals(List<Ingredient> meals) {
        this.meals = meals;
    }
}
