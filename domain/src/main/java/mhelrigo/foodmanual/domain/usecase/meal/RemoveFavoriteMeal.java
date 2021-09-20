package mhelrigo.foodmanual.domain.usecase.meal;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;
import mhelrigo.foodmanual.domain.repository.MealRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class RemoveFavoriteMeal extends UseCase<Completable, RemoveFavoriteMeal.Params> {
    private MealRepository mealRepository;

    @Inject
    public RemoveFavoriteMeal(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Completable execute(Params parameter) {
        return mealRepository.removeFavorite(parameter.mealEntity);
    }

    public static final class Params {
        private final MealEntity mealEntity;

        private Params(MealEntity mealEntity) {
            this.mealEntity = mealEntity;
        }

        public static Params params(MealEntity mealEntity) {
            return new Params(mealEntity);
        }
    }
}
