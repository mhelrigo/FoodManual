package com.mhelrigo.foodmanual.data.remote;

import com.mhelrigo.foodmanual.data.model.api.Categories;
import com.mhelrigo.foodmanual.utils.Constants;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface CategoryApi {
    @GET(Constants.CATEGORIES_URL)
    Flowable<Categories> fetchCategories();
}
