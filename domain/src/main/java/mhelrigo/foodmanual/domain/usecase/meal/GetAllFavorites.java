package mhelrigo.foodmanual.domain.usecase.meal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.meal.Meal;
import mhelrigo.foodmanual.domain.model.meal.Meals;
import mhelrigo.foodmanual.domain.repository.MealRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class GetAllFavorites extends UseCase<Single<List<Meal>>, Void> {
    private MealRepository mealRepository;

    @Inject
    public GetAllFavorites(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Single<List<Meal>> execute(Void parameter) {
        return mealRepository.getAllFavorites();
    }
}
