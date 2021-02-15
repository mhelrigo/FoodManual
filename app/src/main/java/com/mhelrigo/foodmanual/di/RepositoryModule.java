package com.mhelrigo.foodmanual.di;

import com.mhelrigo.foodmanual.data.MealDataSource;
import com.mhelrigo.foodmanual.data.DataRepository;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {
    @Binds
    abstract MealDataSource mealDataSource(DataRepository dataRepository);
}
