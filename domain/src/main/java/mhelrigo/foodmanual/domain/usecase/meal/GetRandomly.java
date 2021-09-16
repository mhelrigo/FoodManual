package mhelrigo.foodmanual.domain.usecase.meal;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.meal.Meals;
import mhelrigo.foodmanual.domain.repository.MealRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class GetRandomly extends UseCase<Single<Meals>, Void> {
    private MealRepository mealRepository;

    @Inject
    public GetRandomly(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Single<Meals> execute(Void parameter) {
        return mealRepository.getRandomly();
    }
}
