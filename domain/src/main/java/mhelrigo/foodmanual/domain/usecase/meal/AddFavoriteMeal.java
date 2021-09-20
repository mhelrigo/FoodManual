package mhelrigo.foodmanual.domain.usecase.meal;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;
import mhelrigo.foodmanual.domain.repository.MealRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class AddFavoriteMeal extends UseCase<Completable, AddFavoriteMeal.Params> {
    private MealRepository mealRepository;

    @Inject
    public AddFavoriteMeal(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Completable execute(AddFavoriteMeal.Params parameter) {
        return mealRepository.addFavorite(parameter.mealEntity);
    }

    public static final class Params {
        public final MealEntity mealEntity;

        private Params(MealEntity mealEntity) {
            this.mealEntity = mealEntity;
        }

        public static Params params(MealEntity mealEntity) {
            return new Params(mealEntity);
        }
    }
}
