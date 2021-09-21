package mhelrigo.foodmanual.data.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import mhelrigo.foodmanual.data.entity.category.CategoriesApiEntity;
import mhelrigo.foodmanual.domain.entity.category.CategoriesEntity;

@Singleton
public class CategoryMapper {
    @Inject
    public CategoryMapper() {
    }

    public CategoriesEntity transform(CategoriesApiEntity categoriesApiEntity) {
        return new CategoriesEntity(categoriesApiEntity.getCategoryEntities());
    }
}
