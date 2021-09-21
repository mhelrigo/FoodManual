package mhelrigo.foodmanual.data.repository.meal.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import mhelrigo.foodmanual.data.entity.meal.MealDatabaseEntity;

@Dao
public interface MealDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable addFavorite(MealDatabaseEntity mealDatabaseEntity);

    @Delete
    Completable removeFavorite(MealDatabaseEntity mealDatabaseEntity);

    @Query("SELECT * FROM MealDatabaseEntity")
    Single<List<MealDatabaseEntity>> getAllFavorites();
}
