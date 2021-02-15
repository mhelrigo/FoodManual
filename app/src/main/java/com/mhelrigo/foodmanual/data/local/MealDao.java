package com.mhelrigo.foodmanual.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mhelrigo.foodmanual.data.model.Meal;

import java.util.List;

import io.reactivex.Flowable;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MealDao {
    @Insert(onConflict = REPLACE)
    void saveMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);

    @Query("DELETE FROM MEAL WHERE idMeal = :id")
    void deleteMealById(String id);

    @Query("SELECT * FROM Meal")
    Flowable<List<Meal>> fetchFavorites();
}
