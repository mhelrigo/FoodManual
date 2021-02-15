package com.mhelrigo.foodmanual.data.model.api;

import com.google.gson.annotations.SerializedName;
import com.mhelrigo.foodmanual.data.model.Ingredient;

import java.util.List;

public class Ingredients {
    @SerializedName("meals")
    private List<Ingredient> ingredients;

    public Ingredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getMeals() {
        return ingredients;
    }

    public void setMeals(List<Ingredient> meals) {
        this.ingredients = meals;
    }
}
