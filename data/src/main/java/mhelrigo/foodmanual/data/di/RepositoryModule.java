package mhelrigo.foodmanual.data.di;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import mhelrigo.foodmanual.data.repository.category.CategoryRepositoryImpl;
import mhelrigo.foodmanual.data.repository.ingredient.IngredientRepositoryImpl;
import mhelrigo.foodmanual.data.repository.meal.MealRepositoryImpl;
import mhelrigo.foodmanual.domain.repository.CategoryRepository;
import mhelrigo.foodmanual.domain.repository.IngredientRepository;
import mhelrigo.foodmanual.domain.repository.MealRepository;

@Module
@InstallIn(SingletonComponent.class)
abstract class RepositoryModule {
    @Binds
    abstract MealRepository mealRepository(MealRepositoryImpl mealRepository);

    @Binds
    abstract CategoryRepository categoryRepository(CategoryRepositoryImpl categoryRepository);

    @Binds
    abstract IngredientRepository ingredientRepository(IngredientRepositoryImpl ingredientRepository);
}
