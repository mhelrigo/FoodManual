package mhelrigo.foodmanual.domain.usecase.meal;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import mhelrigo.foodmanual.domain.entity.meal.MealsEntity;
import mhelrigo.foodmanual.domain.repository.MealRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class SearchMealByName extends UseCase<Single<MealsEntity>, SearchMealByName.Params> {
    private MealRepository mealRepository;

    @Inject
    public SearchMealByName(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Single<MealsEntity> execute(Params parameter) {
        return mealRepository.searchByName(parameter.name);
    }

    public static final class Params {
        public final String name;

        private Params(String name) {
            this.name = name;
        }

        public static Params params(String name) {
            return new Params(name);
        }
    }
}
