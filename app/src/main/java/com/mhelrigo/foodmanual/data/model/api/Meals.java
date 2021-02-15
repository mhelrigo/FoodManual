package com.mhelrigo.foodmanual.data.model.api;

import androidx.room.Entity;

import com.mhelrigo.foodmanual.data.model.Meal;

import java.util.List;

@Entity
public class Meals {
    private List<Meal> meals;

    public Meals() {
    }

    public Meals(List<Meal> mealList) {
        this.meals = mealList;
    }

    public List<Meal> getMealList() {
        return meals;
    }

    public void setMealList(List<Meal> mealList) {
        this.meals = mealList;
    }
}
