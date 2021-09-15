package mhelrigo.foodmanual.domain.usecase.meal;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.meal.Meals;
import mhelrigo.foodmanual.domain.repository.MealRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

public class SearchByCategory extends UseCase<Single<Meals>, SearchByCategory.Params> {
    private MealRepository mealRepository;

    public SearchByCategory(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Single<Meals> execute(SearchByCategory.Params parameter) {
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
