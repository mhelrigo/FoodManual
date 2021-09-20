package mhelrigo.foodmanual.data.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import mhelrigo.foodmanual.data.entity.MealDatabaseEntity;
import mhelrigo.foodmanual.data.repository.meal.local.MealDao;

@Database(entities = {MealDatabaseEntity.class}, version = 1, exportSchema = false)
public abstract class FoodManualDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "FoodManualDatabase";

    public abstract MealDao mealDao();
}
