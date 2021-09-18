package mhelrigo.foodmanual.data.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import mhelrigo.foodmanual.data.BuildConfig;
import mhelrigo.foodmanual.data.repository.area.remote.AreaApi;
import mhelrigo.foodmanual.data.repository.category.remote.CategoryApi;
import mhelrigo.foodmanual.data.repository.ingredient.remote.IngredientApi;
import mhelrigo.foodmanual.data.repository.meal.remote.MealApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class RemoteModule {
    @Singleton
    @Provides
    MealApi mealApi(Retrofit retrofit) {
        return retrofit.create(MealApi.class);
    }

    @Singleton
    @Provides
    CategoryApi categoryApi(Retrofit retrofit) {
        return retrofit.create(CategoryApi.class);
    }

    @Singleton
    @Provides
    AreaApi areaApi(Retrofit retrofit) {
        return retrofit.create(AreaApi.class);
    }

    @Singleton
    @Provides
    IngredientApi ingredientApi(Retrofit retrofit) {
        return retrofit.create(IngredientApi.class);
    }
}
