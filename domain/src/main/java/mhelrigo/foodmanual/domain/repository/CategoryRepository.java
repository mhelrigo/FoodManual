package mhelrigo.foodmanual.domain.repository;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.entity.category.CategoriesEntity;

public interface CategoryRepository {
    Single<CategoriesEntity> getAll();
}
