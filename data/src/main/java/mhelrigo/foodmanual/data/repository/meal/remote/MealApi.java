package mhelrigo.foodmanual.data.repository.meal.remote;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.entity.meal.MealsEntity;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApi {
    @GET("latest.php")
    Single<MealsEntity> getLatest();

    @GET("randomselection.php")
    Single<MealsEntity> getRandomly();

    @GET("lookup.php")
    Single<MealsEntity> getDetails(@Query("i") String id);

    @GET("filter.php")
    Single<MealsEntity> searchByCategory(@Query("c") String category);
}
