package mhelrigo.foodmanual.data.repository.category;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import mhelrigo.foodmanual.data.mapper.CategoryMapper;
import mhelrigo.foodmanual.data.repository.category.remote.CategoryApi;
import mhelrigo.foodmanual.domain.entity.category.CategoriesEntity;
import mhelrigo.foodmanual.domain.repository.CategoryRepository;

@Singleton
public class CategoryRepositoryImpl implements CategoryRepository {
    private CategoryApi categoryApi;
    private CategoryMapper categoryMapper;

    @Inject
    public CategoryRepositoryImpl(CategoryApi categoryApi, CategoryMapper categoryMapper) {
        this.categoryApi = categoryApi;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Single<CategoriesEntity> getAll() {
        return categoryApi.getAll().map(categoriesApiEntity -> categoryMapper.transform(categoriesApiEntity));
    }
}
