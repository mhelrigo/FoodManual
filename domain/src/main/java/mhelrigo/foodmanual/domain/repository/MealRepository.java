package mhelrigo.foodmanual.domain.repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.meal.Meal;
import mhelrigo.foodmanual.domain.model.meal.Meals;

public interface MealRepository {
    Single<Meals> getLatest();
    Single<Meals> getRandomly();
    Single<Meals> getDetails(String id);
    Single<Meals> searchByCategory(String category);
    Completable addFavorite(Meal meal);
    Completable removeFavorite(Meal meal);
    Single<List<Meal>> getAllFavorites();
}
