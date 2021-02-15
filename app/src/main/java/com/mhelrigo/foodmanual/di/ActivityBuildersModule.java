package com.mhelrigo.foodmanual.di;

import com.mhelrigo.foodmanual.ui.category.CategoriesActivity;
import com.mhelrigo.foodmanual.ui.category.CategoriesModule;
import com.mhelrigo.foodmanual.ui.home.HomeActivity;
import com.mhelrigo.foodmanual.ui.home.HomeModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(modules = {HomeModule.class, FragmentBuildersModule.class})
    abstract HomeActivity homeActivity();

    @ContributesAndroidInjector(modules = {CategoriesModule.class, FragmentBuildersModule.class})
    abstract CategoriesActivity categoriesActivity();
}
