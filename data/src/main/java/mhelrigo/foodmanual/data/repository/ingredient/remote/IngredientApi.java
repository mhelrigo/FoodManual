package mhelrigo.foodmanual.data.repository.ingredient.remote;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.ingredient.Ingredients;
import retrofit2.http.GET;

public interface IngredientApi {
    @GET("list.php?i=list")
    Single<Ingredients> getAll();
}
