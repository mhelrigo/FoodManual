package com.mhelrigo.foodmanual.mapper;

import com.mhelrigo.foodmanual.model.category.CategoryModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import mhelrigo.foodmanual.domain.entity.category.CategoryEntity;

@Singleton
public class CategoryModelMapper {
    @Inject
    public CategoryModelMapper() {
    }

    public CategoryModel transform(CategoryEntity categoryEntity) {
        return new CategoryModel(categoryEntity.getIdCategory(), categoryEntity.getStrCategory(), categoryEntity.getStrCategoryThumb(), categoryEntity.getStrCategoryDescription());
    }

    public Observable<List<CategoryModel>> transform(List<CategoryEntity> categories) {
        return Observable.fromIterable(categories).map(categoryEntity -> transform(categoryEntity)).toList().toObservable();
    }
}
