package mhelrigo.foodmanual.domain.usecase.meal;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.entity.meal.MealsEntity;
import mhelrigo.foodmanual.domain.repository.MealRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class SearchMealByCategory extends UseCase<Single<MealsEntity>, SearchMealByCategory.Params> {
    private MealRepository mealRepository;

    @Inject
    public SearchMealByCategory(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Single<MealsEntity> execute(SearchMealByCategory.Params parameter) {
        return mealRepository.searchByCategory(parameter.category);
    }

    public static final class Params {
        public final String category;

        private Params(String category) {
            this.category = category;
        }

        public static Params params(String category) {
            return new Params(category);
        }
    }
}
