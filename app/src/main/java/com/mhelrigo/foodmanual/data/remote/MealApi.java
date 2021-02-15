package com.mhelrigo.foodmanual.data.remote;

import com.mhelrigo.foodmanual.data.model.Meal;
import com.mhelrigo.foodmanual.data.model.api.Ingredients;
import com.mhelrigo.foodmanual.data.model.api.Meals;
import com.mhelrigo.foodmanual.utils.Constants;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface MealApi {
    @GET
    Flowable<Meal> getMealByName(@Url String url);

    @GET(Constants.RANDOM_MEALS_URL)
    Flowable<Meals> fetchRandomMeal();

    @GET(Constants.MEAL_DETAILS_URL)
    Flowable<Meals> fetchMealById(@Query("i") String id);

    @GET(Constants.LATEST_MEALS_URL)
    Flowable<Meals> fetchLatestMeal();

    @GET(Constants.FILTERED_MEALS_URL)
    Single<Meals> fetchMealsFilteredByCategory(@Query("c")String filters);

    @GET(Constants.SEARCH_MEALS_URL)
    Flowable<Meals> fetchMealsByCharacter(@Query("s") String filters);
}
