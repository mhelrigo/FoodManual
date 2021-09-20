package mhelrigo.foodmanual.domain.usecase.meal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;
import mhelrigo.foodmanual.domain.repository.MealRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class GetAllFavoriteMeal extends UseCase<Single<List<MealEntity>>, Void> {
    private MealRepository mealRepository;

    @Inject
    public GetAllFavoriteMeal(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Single<List<MealEntity>> execute(Void parameter) {
        return mealRepository.getAllFavorites();
    }
}
