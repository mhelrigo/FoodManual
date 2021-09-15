package mhelrigo.foodmanual.domain.repository;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.category.Categories;

public interface CategoryRepository {
    Single<Categories> getAll();
}
