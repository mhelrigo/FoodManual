package com.mhelrigo.foodmanual;

import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;
import mhelrigo.foodmanual.domain.entity.meal.MealsEntity;
import mhelrigo.foodmanual.domain.usecase.meal.GetAllFavoriteMeal;
import mhelrigo.foodmanual.domain.usecase.meal.GetLatestMeals;

@HiltViewModel
public class MealViewModel extends ViewModel {
    private GetLatestMeals getLatestMeals;
    private GetAllFavoriteMeal getAllFavoriteMeal;
    private CompositeDisposable compositeDisposable;

    @Inject
    public MealViewModel(GetLatestMeals getLatestMeals, GetAllFavoriteMeal getAllFavoriteMeal) {
        this.getLatestMeals = getLatestMeals;
        this.getAllFavoriteMeal = getAllFavoriteMeal;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void requestForLatestMeal() {
        BiFunction<MealsEntity, List<MealEntity>, Pair<MealsEntity, List<MealEntity>>> zipper = (BiFunction<MealsEntity, List<MealEntity>, Pair<MealsEntity, List<MealEntity>>>) (meals, meals2) -> new Pair(meals, meals2);

        compositeDisposable.add(getLatestMeals.execute(null)
                .zipWith(getAllFavoriteMeal.execute(null), zipper)
                .flatMapObservable(new Function<Pair<MealsEntity, List<MealEntity>>, ObservableSource<List<MealEntity>>>() {
                    @Override
                    public ObservableSource<List<MealEntity>> apply(@NonNull Pair<MealsEntity, List<MealEntity>> mealsListPair) throws Exception {
                        MealsEntity latestMealsEntity = mealsListPair.first;
                        List<MealEntity> favoritesMealEntities = mealsListPair.second;

                        Log.e("MealViewModel", "latestMeals: " + latestMealsEntity.getMeals().size());
                        Log.e("MealViewModel", "favoritesMeals: " + favoritesMealEntities.size());

                        return Observable.fromIterable(latestMealsEntity.getMeals())
                                .concatMap(meal -> {
                                    return Observable.fromIterable(favoritesMealEntities)
                                            .filter(meal1 -> {
                                                return meal.getIdMeal() == meal1.getIdMeal();
                                            }).first(meal)
                                            .map(meal1 -> {
                                                return latestMealsEntity.getMeals();
                                            }).toObservable();
                                });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MealEntity>>() {
                    @Override
                    public void accept(List<MealEntity> mealEntities) throws Exception {
                        Log.e("MealViewModel", "Size: " + mealEntities.size());
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
