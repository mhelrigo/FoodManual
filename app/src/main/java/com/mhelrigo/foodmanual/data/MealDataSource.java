package com.mhelrigo.foodmanual.data;

import androidx.lifecycle.LiveData;

import com.mhelrigo.foodmanual.data.model.Meal;
import com.mhelrigo.foodmanual.data.model.api.Meals;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface MealDataSource {
    Flowable<Meals> fetchRandomMeals();
    Flowable<Meals> fetchMealById(String id);
    Flowable<Meals> fetchLatestMeals();
    Single<Meals> fetchMealsFilteredByCategory(String filters);
    void saveMeal(Meal meal);
    void deleteMeal(Meal meal);
    Flowable<List<Meal>> fetchFavorites();
    Flowable<Meals> fetchMealsByCharacter(String filters);
}
