package mhelrigo.foodmanual.domain.usecase.meal;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.entity.meal.MealsEntity;
import mhelrigo.foodmanual.domain.repository.MealRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class FilterByMainIngredient extends UseCase<Single<MealsEntity>, FilterByMainIngredient.Params> {
    private MealRepository mealRepository;

    @Inject
    public FilterByMainIngredient(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Single<MealsEntity> execute(Params parameter) {
        return mealRepository.filterByMainIngredient(parameter.ingredient);
    }

    public static final class Params {
        public final String ingredient;

        private Params(String ingredient) {
            this.ingredient = ingredient;
        }

        public static Params params(String ingredient) {
            return new Params(ingredient);
        }
    }
}
