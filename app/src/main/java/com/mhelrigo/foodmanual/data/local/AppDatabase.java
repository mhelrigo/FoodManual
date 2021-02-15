package com.mhelrigo.foodmanual.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mhelrigo.foodmanual.data.model.Meal;
import com.mhelrigo.foodmanual.data.model.api.Meals;

@Database(entities = {Meal.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    public abstract MealDao mealDao();
}
