package mhelrigo.foodmanual.data.repository.meal.remote;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.meal.Meals;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApi {
    @GET("latest.php")
    Single<Meals> getLatest();

    @GET("randomselection.php")
    Single<Meals> getRandomly();

    @GET("lookup.php")
    Single<Meals> getDetails(@Query("i") String id);

    @GET("filter.php")
    Single<Meals> searchByCategory(@Query("c") String category);
}
