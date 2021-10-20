package mhelrigo.foodmanual.domain.usecase.meal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class MarkFavoriteMeal extends UseCase<Single<List<MealEntity>>, MarkFavoriteMeal.Params> {
    @Inject
    public MarkFavoriteMeal() {
    }

    @Override
    public Single<List<MealEntity>> execute(Params parameter) {
        return Observable.fromIterable(parameter.mealEntities)
                .map(v0 -> {
                    if (Integer.parseInt(v0.getIdMeal()) == Integer.parseInt(parameter.mealEntity.getIdMeal())) {
                        v0.setFavorite(!parameter.mealEntity.isFavorite());
                    }

                    return v0;
                }).toList();
    }

    public static final class Params {
        public List<MealEntity> mealEntities;
        public MealEntity mealEntity;

        private Params(List<MealEntity> mealEntities, MealEntity mealEntity) {
            this.mealEntities = mealEntities;
            this.mealEntity = mealEntity;
        }

        public static Params params(List<MealEntity> mealEntities, MealEntity mealEntity) {
            return new Params(mealEntities, mealEntity);
        }
    }
}
