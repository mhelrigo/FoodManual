package com.mhelrigo.foodmanual.di;

import com.mhelrigo.foodmanual.ui.category.categorydetails.CategoryDetailFragment;
import com.mhelrigo.foodmanual.ui.category.categorylist.CategoryListFragment;
import com.mhelrigo.foodmanual.ui.home.favoritemeals.FavoriteMealFragment;
import com.mhelrigo.foodmanual.ui.home.latestmeals.LatestMealFragment;
import com.mhelrigo.foodmanual.ui.home.randommeals.RandomMealsFragment;
import com.mhelrigo.foodmanual.ui.mealdetails.MealDetailsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract LatestMealFragment latestMealFragment();

    @ContributesAndroidInjector
    abstract MealDetailsFragment mealDetailsFragment();

    @ContributesAndroidInjector
    abstract CategoryListFragment categoryListFragment();

    @ContributesAndroidInjector
    abstract CategoryDetailFragment categoryDetailFragment();

    @ContributesAndroidInjector
    abstract RandomMealsFragment randomMealsFragment();

    @ContributesAndroidInjector
    abstract FavoriteMealFragment favoriteMealFragment();
}
