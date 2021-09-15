package mhelrigo.foodmanual.domain.usecase.meal;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.meal.Meals;
import mhelrigo.foodmanual.domain.repository.MealRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

public class GetLatest extends UseCase<Single<Meals>, Void> {
    private MealRepository mealRepository;

    public GetLatest(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Single<Meals> execute(Void parameter) {
        return mealRepository.getLatest();
    }
}
