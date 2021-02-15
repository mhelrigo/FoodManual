package com.mhelrigo.foodmanual.data;

import com.mhelrigo.foodmanual.data.model.api.Categories;

import io.reactivex.Flowable;

public interface CategoryDataSource {
    Flowable<Categories> fetchCategories();
}
