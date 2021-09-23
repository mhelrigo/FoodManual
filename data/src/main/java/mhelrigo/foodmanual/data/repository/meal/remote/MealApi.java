package mhelrigo.foodmanual.data.repository.meal.remote;

import io.reactivex.Single;
import mhelrigo.foodmanual.data.entity.meal.MealsApiEntity;
import mhelrigo.foodmanual.domain.entity.meal.MealsEntity;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApi {
    @GET("latest.php")
    Single<MealsApiEntity> getLatest();

    @GET("randomselection.php")
    Single<MealsApiEntity> getRandomly();

    @GET("lookup.php")
    Single<MealsApiEntity> getDetails(@Query("i") String id);

    @GET("filter.php")
    Single<MealsApiEntity> searchByCategory(@Query("c") String category);

    @GET("search.php")
    Single<MealsApiEntity> searchByName(@Query("s") String name);

    @GET("filter.php")
    Single<MealsApiEntity> filterByMainIngredient(@Query("i") String ingredient);
}
