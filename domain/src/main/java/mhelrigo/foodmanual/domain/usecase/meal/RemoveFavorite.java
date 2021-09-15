package mhelrigo.foodmanual.domain.usecase.meal;

import io.reactivex.Completable;
import mhelrigo.foodmanual.domain.model.meal.Meal;
import mhelrigo.foodmanual.domain.repository.MealRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

public class RemoveFavorite extends UseCase<Completable, RemoveFavorite.Params> {
    private MealRepository mealRepository;

    public RemoveFavorite(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Completable execute(Params parameter) {
        return mealRepository.removeFavorite(parameter.meal);
    }

    public static final class Params {
        private final Meal meal;

        private Params(Meal meal) {
            this.meal = meal;
        }

        public static Params params(Meal meal) {
            return new Params(meal);
        }
    }
}