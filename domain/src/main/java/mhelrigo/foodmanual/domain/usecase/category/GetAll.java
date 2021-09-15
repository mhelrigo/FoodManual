package mhelrigo.foodmanual.domain.usecase.category;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.category.Categories;
import mhelrigo.foodmanual.domain.repository.CategoryRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

public class GetAll extends UseCase<Single<Categories>, Void> {
    private CategoryRepository categoryRepository;

    public GetAll(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Single<Categories> execute(Void parameter) {
        return categoryRepository.getAll();
    }
}
