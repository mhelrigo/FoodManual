package com.mhelrigo.foodmanual.data;

import com.mhelrigo.foodmanual.data.model.api.Ingredients;

import io.reactivex.Flowable;

public interface IngredientDataSource {
    Flowable<Ingredients> fetchIngredients();
}
