package mhelrigo.foodmanual.data.repository.category.remote;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.category.Categories;
import retrofit2.http.GET;

public interface CategoryApi {
    @GET("categories.php")
    Single<Categories> getAll();
}
