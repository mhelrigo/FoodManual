package com.mhelrigo.foodmanual;

import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import mhelrigo.foodmanual.domain.model.meal.Meal;
import mhelrigo.foodmanual.domain.model.meal.Meals;
import mhelrigo.foodmanual.domain.usecase.meal.GetAllFavorites;
import mhelrigo.foodmanual.domain.usecase.meal.GetLatest;

@HiltViewModel
public class MealViewModel extends ViewModel {
    private GetLatest getLatest;
    private GetAllFavorites getAllFavorites;
    private CompositeDisposable compositeDisposable;

    @Inject
    public MealViewModel(GetLatest getLatest, GetAllFavorites getAllFavorites) {
        this.getLatest = getLatest;
        this.getAllFavorites = getAllFavorites;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void requestForLatestMeal() {
        BiFunction<Meals, List<Meal>, Pair<Meals, List<Meal>>> zipper = (BiFunction<Meals, List<Meal>, Pair<Meals, List<Meal>>>) (meals, meals2) -> new Pair(meals, meals2);

        compositeDisposable.add(getLatest.execute(null)
                .zipWith(getAllFavorites.execute(null), zipper)
                .flatMapObservable(new Function<Pair<Meals, List<Meal>>, ObservableSource<List<Meal>>>() {
                    @Override
                    public ObservableSource<List<Meal>> apply(@NonNull Pair<Meals, List<Meal>> mealsListPair) throws Exception {
                        Meals latestMeals = mealsListPair.first;
                        List<Meal> favoritesMeals = mealsListPair.second;

                        Log.e("MealViewModel", "latestMeals: " + latestMeals.getMeals().size());
                        Log.e("MealViewModel", "favoritesMeals: " + favoritesMeals.size());

                        return Observable.fromIterable(latestMeals.getMeals())
                                .concatMap(meal -> {
                                    return Observable.fromIterable(favoritesMeals)
                                            .filter(meal1 -> {
                                                return meal.getIdMeal() == meal1.getIdMeal();
                                            }).first(meal)
                                            .map(meal1 -> {
                                                return latestMeals.getMeals();
                                            }).toObservable();
                                });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Meal>>() {
                    @Override
                    public void accept(List<Meal> meals) throws Exception {
                        Log.e("MealViewModel", "Size: " + meals.size());
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
