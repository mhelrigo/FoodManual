package mhelrigo.foodmanual.domain.usecase.category;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.entity.category.CategoriesEntity;
import mhelrigo.foodmanual.domain.repository.CategoryRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class GetAllCategory extends UseCase<Single<CategoriesEntity>, Void> {
    private CategoryRepository categoryRepository;

    @Inject
    public GetAllCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Single<CategoriesEntity> execute(Void parameter) {
        return categoryRepository.getAll();
    }
}
