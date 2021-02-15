package com.mhelrigo.foodmanual.di;

import android.app.Application;

import com.mhelrigo.foodmanual.BaseApplication;
import com.mhelrigo.foodmanual.MealViewModel;
import com.mhelrigo.foodmanual.data.DataRepository;
import com.mhelrigo.foodmanual.ui.category.CategoriesViewModel;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.BindsInstance;
import dagger.Component;
import dagger.Provides;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.multibindings.IntoMap;

@Singleton
@Component (modules = {AndroidSupportInjectionModule.class,
        ViewModelFactoryModule.class,
        AppModule.class,
        ActivityBuildersModule.class,
        RepositoryModule.class})
public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
