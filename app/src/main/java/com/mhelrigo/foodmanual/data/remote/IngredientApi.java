package com.mhelrigo.foodmanual.data.remote;

import com.mhelrigo.foodmanual.data.model.api.Ingredients;
import com.mhelrigo.foodmanual.utils.Constants;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface IngredientApi {
    @GET(Constants.INGREDIENTS_URL)
    Flowable<Ingredients> fetchIngredients();
}
