package mhelrigo.foodmanual.domain.usecase.meal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class MarkAllFavoriteMeal extends UseCase<Observable<MealEntity>, MarkAllFavoriteMeal.Params> {

    @Inject
    public MarkAllFavoriteMeal() {
    }

    @Override
    public Observable<MealEntity> execute(Params parameter) {
        return Observable.fromIterable(parameter.toBeMarked)
                .subscribeOn(Schedulers.computation())
                .concatMap(toBeMarked ->
                Observable.fromIterable(parameter.favorites)
                        .filter(favorite -> toBeMarked.getIdMeal().equals(favorite.getIdMeal())).map(meal -> {
                    meal.setFavorite(true);
                    return meal;
                }).first(toBeMarked).toObservable()
        );
    }

    public static final class Params {
        public final List<MealEntity> toBeMarked;
        public final List<MealEntity> favorites;

        private Params(List<MealEntity> toBeMarked, List<MealEntity> favorites) {
            this.toBeMarked = toBeMarked;
            this.favorites = favorites;
        }

        public static Params params(List<MealEntity> toBeMarked, List<MealEntity> favorites) {
            return new Params(toBeMarked, favorites);
        }
    }
}
