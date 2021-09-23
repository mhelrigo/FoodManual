package com.mhelrigo.foodmanual.ui.meal;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mhelrigo.foodmanual.mapper.MealModelMapper;
import com.mhelrigo.foodmanual.model.meal.MealModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;
import mhelrigo.foodmanual.domain.entity.meal.MealsEntity;
import mhelrigo.foodmanual.domain.usecase.meal.AddFavoriteMeal;
import mhelrigo.foodmanual.domain.usecase.meal.FilterByMainIngredient;
import mhelrigo.foodmanual.domain.usecase.meal.GetAllFavoriteMeal;
import mhelrigo.foodmanual.domain.usecase.meal.GetLatestMeals;
import mhelrigo.foodmanual.domain.usecase.meal.GetMealDetails;
import mhelrigo.foodmanual.domain.usecase.meal.MarkAllFavoriteMeal;
import mhelrigo.foodmanual.domain.usecase.meal.RemoveFavoriteMeal;
import mhelrigo.foodmanual.domain.usecase.meal.SearchMealByName;
import timber.log.Timber;

@HiltViewModel
public class MealViewModel extends ViewModel {
    private GetLatestMeals getLatestMeals;
    private GetAllFavoriteMeal getAllFavoriteMeal;
    private MarkAllFavoriteMeal markAllFavoriteMeal;
    private AddFavoriteMeal addFavoriteMeal;
    private RemoveFavoriteMeal removeFavoriteMeal;
    private SearchMealByName searchMealByName;
    private FilterByMainIngredient filterByMainIngredient;
    private GetMealDetails getMealDetails;
    private MealModelMapper mealModelMapper;

    public CompositeDisposable compositeDisposable;

    private MutableLiveData<List<MealModel>> latestMeals;

    public LiveData<List<MealModel>> latestMeals() {
        return latestMeals;
    }

    private MutableLiveData<List<MealModel>> searchedMeals;

    public LiveData<List<MealModel>> searchedMeals() {
        return searchedMeals;
    }

    private MutableLiveData<List<MealModel>> favoriteMeals;

    public LiveData<List<MealModel>> favoriteMeals() {
        return favoriteMeals;
    }

    private MutableLiveData<List<MealModel>> mealsFilteredByMainIngredient;

    public LiveData<List<MealModel>> mealsFilteredByMainIngredient() {
        return mealsFilteredByMainIngredient;
    }

    private MutableLiveData<MealModel> meal;

    public LiveData<MealModel> meal() {
        return meal;
    }

    private Disposable disposableForSearchingMealByName;

    @Inject
    public MealViewModel(GetLatestMeals getLatestMeals,
                         GetAllFavoriteMeal getAllFavoriteMeal,
                         MarkAllFavoriteMeal markAllFavoriteMeal,
                         AddFavoriteMeal addFavoriteMeal,
                         RemoveFavoriteMeal removeFavoriteMeal,
                         SearchMealByName searchMealByName,
                         FilterByMainIngredient filterByMainIngredient,
                         GetMealDetails getMealDetails,
                         MealModelMapper mealModelMapper) {
        this.getLatestMeals = getLatestMeals;
        this.getAllFavoriteMeal = getAllFavoriteMeal;
        this.markAllFavoriteMeal = markAllFavoriteMeal;
        this.addFavoriteMeal = addFavoriteMeal;
        this.removeFavoriteMeal = removeFavoriteMeal;
        this.searchMealByName = searchMealByName;
        this.filterByMainIngredient = filterByMainIngredient;
        this.getMealDetails = getMealDetails;
        this.mealModelMapper = mealModelMapper;

        compositeDisposable = new CompositeDisposable();

        latestMeals = new MutableLiveData<>();
        searchedMeals = new MutableLiveData<>();
        favoriteMeals = new MutableLiveData<>();
        mealsFilteredByMainIngredient = new MutableLiveData<>();
        meal = new MutableLiveData<>();
    }

    public void requestForLatestMeal() {
        compositeDisposable.add(getLatestMeals.execute(null)
                .zipWith(getAllFavoriteMeal.execute(null), ((mealsEntity, mealEntities) -> new Pair<>(mealsEntity, mealEntities)))
                .flatMapObservable(mealsEntityListPair -> {
                    List<MealEntity> latest = mealsEntityListPair.first.getMeals();
                    List<MealEntity> favorites = mealsEntityListPair.second;

                    return mealModelMapper.transform(markAllFavoriteMeal.execute(MarkAllFavoriteMeal.Params.params(latest, favorites)));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealModels -> latestMeals.postValue(mealModels)));
    }

    public void requestForFavoriteMeals() {
        compositeDisposable.add(getAllFavoriteMeal.execute(null)
                .flatMapObservable(mealEntities -> Observable.fromIterable(mealEntities))
                .map(mealEntity -> mealModelMapper.transform(mealEntity))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealModels -> favoriteMeals.postValue(mealModels)));
    }

    public void searchForMealsByName(CharSequence p0) {
        if (disposableForSearchingMealByName != null) {
            if (disposableForSearchingMealByName.isDisposed()) {
                disposableForSearchingMealByName.dispose();
            }
        }

        if (p0.length() <= 0) {
            return;
        }

        disposableForSearchingMealByName = searchMealByName.execute(SearchMealByName.Params.params(p0.toString()))
                .zipWith(getAllFavoriteMeal.execute(null), (mealsEntity, mealEntities) -> new Pair<>(mealsEntity, mealEntities))
                .flatMapObservable(mealsEntityListPair -> {
                    List<MealEntity> latest = mealsEntityListPair.first.getMeals();
                    List<MealEntity> favorites = mealsEntityListPair.second;

                    return mealModelMapper.transform(markAllFavoriteMeal.execute(MarkAllFavoriteMeal.Params.params(latest, favorites)));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealModels -> searchedMeals.postValue(mealModels),
                        throwable -> Timber.e(throwable.getMessage()));

        compositeDisposable.add(disposableForSearchingMealByName);
    }

    public void filterMealsByMainIngredient(String p0) {
        compositeDisposable.add(filterByMainIngredient.execute(FilterByMainIngredient.Params.params(p0))
                .zipWith(getAllFavoriteMeal.execute(null), (mealsEntity, mealEntities) -> new Pair<>(mealsEntity, mealEntities))
                .flatMapObservable(mealsEntityListPair -> {
                    List<MealEntity> filteredByMainIngredient = mealsEntityListPair.first.getMeals();
                    List<MealEntity> favorites = mealsEntityListPair.second;

                    return mealModelMapper.transform(markAllFavoriteMeal.execute(MarkAllFavoriteMeal.Params.params(filteredByMainIngredient, favorites)));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealModels -> mealsFilteredByMainIngredient.postValue(mealModels)));
    }

    public void requestForExpandedMealDetail(String p0) {
        compositeDisposable.add(getMealDetails.execute(GetMealDetails.Params.params(p0))
                .zipWith(getAllFavoriteMeal.execute(null), (mealsEntity, mealEntities) -> new Pair<>(mealsEntity, mealEntities))
                .flatMapObservable(mealsEntityListPair -> {
                    List<MealEntity> meals = mealsEntityListPair.first.getMeals();
                    List<MealEntity> favorites = mealsEntityListPair.second;

                    return mealModelMapper.transform(markAllFavoriteMeal.execute(MarkAllFavoriteMeal.Params.params(meals, favorites)));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealModels -> {
                    meal.postValue(mealModels.get(0));
                }));
    }

    public Completable cancelSearchByName() {
        requestForLatestMeal();
        return Completable.complete();
    }

    public Completable toggleFavoriteOfAMeal(MealModel mealModel) {
        mealModel.setFavorite(!mealModel.isFavorite());

        if (mealModel.isFavorite()) {
            return addFavoriteMeal.execute(AddFavoriteMeal.Params.params(mealModelMapper.transform(mealModel))).subscribeOn(Schedulers.io());
        } else if (!mealModel.isFavorite()) {
            return removeFavoriteMeal.execute(RemoveFavoriteMeal.Params.params(mealModelMapper.transform(mealModel))).subscribeOn(Schedulers.io());
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}