package mhelrigo.foodmanual.data.repository.category;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import mhelrigo.foodmanual.data.repository.category.remote.CategoryApi;
import mhelrigo.foodmanual.domain.model.category.Categories;
import mhelrigo.foodmanual.domain.repository.CategoryRepository;

@Singleton
public class CategoryRepositoryImpl implements CategoryRepository {
    private CategoryApi categoryApi;

    @Inject
    public CategoryRepositoryImpl(CategoryApi categoryApi) {
        this.categoryApi = categoryApi;
    }

    @Override
    public Single<Categories> getAll() {
        return categoryApi.getAll();
    }
}