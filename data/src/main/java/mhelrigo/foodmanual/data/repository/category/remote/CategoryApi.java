package mhelrigo.foodmanual.data.repository.category.remote;

import io.reactivex.Single;
import mhelrigo.foodmanual.data.entity.category.CategoriesApiEntity;
import retrofit2.http.GET;

public interface CategoryApi {
    @GET("categories.php")
    Single<CategoriesApiEntity> getAll();
}
