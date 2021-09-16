package mhelrigo.foodmanual.data.repository.meal.local;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import mhelrigo.foodmanual.data.model.Meal;

@Dao
public interface MealDao {
    @Insert(onConflict = REPLACE)
    Completable addFavorite(Meal meal);

    @Delete
    Completable removeFavorite(Meal meal);

    @Query("SELECT * FROM Meal")
    Single<List<Meal>> getAllFavorites();
}
