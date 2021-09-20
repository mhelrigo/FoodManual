package mhelrigo.foodmanual.domain.repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;
import mhelrigo.foodmanual.domain.entity.meal.MealsEntity;

public interface MealRepository {
    Single<MealsEntity> getLatest();
    Single<MealsEntity> getRandomly();
    Single<MealsEntity> getDetails(String id);
    Single<MealsEntity> searchByCategory(String category);
    Completable addFavorite(MealEntity mealEntity);
    Completable removeFavorite(MealEntity mealEntity);
    Single<List<MealEntity>> getAllFavorites();
}
