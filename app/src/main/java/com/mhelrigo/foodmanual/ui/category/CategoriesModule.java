package com.mhelrigo.foodmanual.ui.category;

import androidx.lifecycle.ViewModel;

import com.mhelrigo.foodmanual.di.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class CategoriesModule {
    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel.class)
    public abstract ViewModel categoriesViewModel(CategoriesViewModel categoriesViewModel);
}
