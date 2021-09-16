package mhelrigo.foodmanual.domain.usecase.meal;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import mhelrigo.foodmanual.domain.model.meal.Meal;
import mhelrigo.foodmanual.domain.repository.MealRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class AddFavorite extends UseCase<Completable, AddFavorite.Params> {
    private MealRepository mealRepository;

    @Inject
    public AddFavorite(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Completable execute(AddFavorite.Params parameter) {
        return mealRepository.addFavorite(parameter.meal);
    }

    public static final class Params {
        public final Meal meal;

        private Params(Meal meal) {
            this.meal = meal;
        }

        public static Params params(Meal meal) {
            return new Params(meal);
        }
    }
}
