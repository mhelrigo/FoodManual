package mhelrigo.foodmanual.data.repository.category.remote;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.entity.category.CategoriesEntity;
import retrofit2.http.GET;

public interface CategoryApi {
    @GET("categories.php")
    Single<CategoriesEntity> getAll();
}
