package com.mhelrigo.foodmanual.di;

import com.mhelrigo.foodmanual.data.MealDataSource;
import com.mhelrigo.foodmanual.data.DataRepository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {
    @Binds
    abstract MealDataSource mealDataSource(DataRepository dataRepository);
}
