package com.mhelrigo.foodmanual.data;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.mhelrigo.foodmanual.data.local.AppDatabase;
import com.mhelrigo.foodmanual.data.model.Meal;
import com.mhelrigo.foodmanual.data.model.api.Areas;
import com.mhelrigo.foodmanual.data.model.api.Categories;
import com.mhelrigo.foodmanual.data.model.api.Ingredients;
import com.mhelrigo.foodmanual.data.model.api.Meals;
import com.mhelrigo.foodmanual.data.remote.AreaApi;
import com.mhelrigo.foodmanual.data.remote.CategoryApi;
import com.mhelrigo.foodmanual.data.remote.IngredientApi;
import com.mhelrigo.foodmanual.data.remote.MealApi;
import com.mhelrigo.foodmanual.utils.Constants;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Retrofit;

@Singleton
public class DataRepository implements AreaDataSource, IngredientDataSource, MealDataSource, CategoryDataSource{
    private static final String TAG = "DataRepository";

    AreaApi areaApi;
    IngredientApi ingredientApi;
    MealApi mealApi;
    CategoryApi categoryApi;
    SharedPreferences sharedPreferences;

    private AppDatabase appDatabase;

    @Inject
    DataRepository(Retrofit retrofit, SharedPreferences sharedPreferences, AppDatabase appDatabase){
        this.sharedPreferences = sharedPreferences;
        this.appDatabase = appDatabase;

        mealApi = retrofit.create(MealApi.class);
        categoryApi = retrofit.create(CategoryApi.class);
        areaApi = retrofit.create(AreaApi.class);
        ingredientApi = retrofit.create(IngredientApi.class);
        Log.e(TAG, TAG + " Ready...");
    }

    @Override
    public Flowable<Meals> fetchRandomMeals() {
        return mealApi.fetchRandomMeal();
    }

    @Override
    public Flowable<Meals> fetchMealById(String id) {
        return mealApi.fetchMealById(id);
    }

    @Override
    public Flowable<Ingredients> fetchIngredients() {
        return ingredientApi.fetchIngredients();
    }

    @Override
    public Flowable<Meals> fetchLatestMeals() {
        return mealApi.fetchLatestMeal();
    }

    @Override
    public Single<Meals> fetchMealsFilteredByCategory(String filters) {
        return mealApi.fetchMealsFilteredByCategory(filters);
    }

    @Override
    public void saveMeal(Meal meal) {
        appDatabase.mealDao().saveMeal(meal);
    }

    @Override
    public void deleteMeal(Meal meal) {
        appDatabase.mealDao().deleteMealById(meal.getIdMeal());
    }

    @Override
    public Flowable<List<Meal>> fetchFavorites() {
        return appDatabase.mealDao().fetchFavorites();
    }

    @Override
    public Flowable<Meals> fetchMealsByCharacter(String filters) {
        return mealApi.fetchMealsByCharacter(filters);
    }

    @Override
    public Flowable<Categories> fetchCategories() {
        return categoryApi.fetchCategories();
    }

    @Override
    public Flowable<Areas> fetchAreas() {
        return areaApi.fetchAreas();
    }
}
