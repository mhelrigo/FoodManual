package com.mhelrigo.foodmanual.di;

import androidx.lifecycle.ViewModelProvider;

import com.mhelrigo.foodmanual.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {
    @Binds
    public abstract ViewModelProvider.Factory factory(ViewModelProviderFactory viewModelProviderFactory);
}
