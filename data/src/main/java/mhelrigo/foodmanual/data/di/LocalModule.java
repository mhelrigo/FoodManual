package mhelrigo.foodmanual.data.di;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import mhelrigo.foodmanual.data.repository.FoodManualDatabase;
import mhelrigo.foodmanual.data.repository.meal.local.MealDao;

@Module
@InstallIn(SingletonComponent.class)
public class LocalModule {
    @Singleton
    @Provides
    RoomDatabase database(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, FoodManualDatabase.class, FoodManualDatabase.DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    MealDao mealDao(FoodManualDatabase foodManualDatabase) {
        return foodManualDatabase.mealDao();
    }
}
