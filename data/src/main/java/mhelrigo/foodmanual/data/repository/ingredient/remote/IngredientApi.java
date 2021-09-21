package mhelrigo.foodmanual.data.repository.ingredient.remote;

import io.reactivex.Single;
import mhelrigo.foodmanual.data.entity.ingredient.IngredientApiEntity;
import mhelrigo.foodmanual.domain.entity.ingredient.IngredientsEntity;
import retrofit2.http.GET;

public interface IngredientApi {
    @GET("list.php?i=list")
    Single<IngredientApiEntity> getAll();
}
