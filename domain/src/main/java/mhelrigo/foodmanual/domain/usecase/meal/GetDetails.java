package mhelrigo.foodmanual.domain.usecase.meal;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.meal.Meals;
import mhelrigo.foodmanual.domain.repository.MealRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

public class GetDetails extends UseCase<Single<Meals>, GetDetails.Params> {
    private MealRepository mealRepository;

    public GetDetails(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Single<Meals> execute(Params parameter) {
        return mealRepository.getDetails(parameter.id);
    }

    public static final class Params {
        private final String id;

        private Params(String id) {
            this.id = id;
        }

        public static Params params(String id) {
            return new Params(id);
        }
    }
}
