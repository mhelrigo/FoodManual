package mhelrigo.foodmanual.domain.usecase.meal;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.entity.meal.MealsEntity;
import mhelrigo.foodmanual.domain.repository.MealRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class GetRandomMeals extends UseCase<Single<MealsEntity>, Void> {
    private MealRepository mealRepository;

    @Inject
    public GetRandomMeals(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Single<MealsEntity> execute(Void parameter) {
        return mealRepository.getRandomly();
    }
}
