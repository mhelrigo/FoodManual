package com.mhelrigo.foodmanual.ui.meal;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mhelrigo.foodmanual.mapper.MealModelMapper;
import com.mhelrigo.foodmanual.model.meal.MealModel;
import com.mhelrigo.foodmanual.ui.commons.base.ViewStateWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;
import mhelrigo.foodmanual.domain.usecase.meal.AddFavoriteMeal;
import mhelrigo.foodmanual.domain.usecase.meal.FilterByMainIngredient;
import mhelrigo.foodmanual.domain.usecase.meal.FilterMealByCategory;
import mhelrigo.foodmanual.domain.usecase.meal.GetAllFavoriteMeal;
import mhelrigo.foodmanual.domain.usecase.meal.GetLatestMeals;
import mhelrigo.foodmanual.domain.usecase.meal.GetMealDetails;
import mhelrigo.foodmanual.domain.usecase.meal.MarkAllFavoriteMeal;
import mhelrigo.foodmanual.domain.usecase.meal.MarkFavoriteMeal;
import mhelrigo.foodmanual.domain.usecase.meal.RemoveFavoriteMeal;
import mhelrigo.foodmanual.domain.usecase.meal.SearchMealByName;
import timber.log.Timber;

@HiltViewModel
public class MealViewModel extends ViewModel implements SyncMeals {
    public static final int SYNC_MEALS_DEFAULT_INDEX = -1;

    private GetLatestMeals getLatestMeals;
    private GetAllFavoriteMeal getAllFavoriteMeal;
    private MarkAllFavoriteMeal markAllFavoriteMeal;
    private AddFavoriteMeal addFavoriteMeal;
    private RemoveFavoriteMeal removeFavoriteMeal;
    private SearchMealByName searchMealByName;
    private FilterByMainIngredient filterByMainIngredient;
    private GetMealDetails getMealDetails;
    private FilterMealByCategory filterMealByCategory;
    private MarkFavoriteMeal markFavoriteMeal;
    private MealModelMapper mealModelMapper;

    public CompositeDisposable compositeDisposable;

    private MutableLiveData<ViewStateWrapper<List<MealModel>>> latestMeals;

    public LiveData<ViewStateWrapper<List<MealModel>>> latestMeals() {
        return latestMeals;
    }

    private MutableLiveData<ViewStateWrapper<List<MealModel>>> searchedMeals;

    public LiveData<ViewStateWrapper<List<MealModel>>> searchedMeals() {
        return searchedMeals;
    }

    private MutableLiveData<ViewStateWrapper<List<MealModel>>> favoriteMeals;

    public LiveData<ViewStateWrapper<List<MealModel>>> favoriteMeals() {
        return favoriteMeals;
    }

    private MutableLiveData<ViewStateWrapper<List<MealModel>>> mealsFilteredByMainIngredient;

    public LiveData<ViewStateWrapper<List<MealModel>>> mealsFilteredByMainIngredient() {
        return mealsFilteredByMainIngredient;
    }

    private MutableLiveData<ViewStateWrapper<MealModel>> meal;

    public LiveData<ViewStateWrapper<MealModel>> meal() {
        return meal;
    }

    private MutableLiveData<ViewStateWrapper<List<MealModel>>> mealsFilteredByCategory;

    public LiveData<ViewStateWrapper<List<MealModel>>> mealsFilteredByCategory() {
        return mealsFilteredByCategory;
    }

    private MutableLiveData<String> mealToBeSearched;

    public LiveData<String> mealIdToBeSearched() {
        return mealToBeSearched;
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
                         FilterMealByCategory filterMealByCategory,
                         MarkFavoriteMeal markFavoriteMeal,
                         MealModelMapper mealModelMapper) {
        this.getLatestMeals = getLatestMeals;
        this.getAllFavoriteMeal = getAllFavoriteMeal;
        this.markAllFavoriteMeal = markAllFavoriteMeal;
        this.addFavoriteMeal = addFavoriteMeal;
        this.removeFavoriteMeal = removeFavoriteMeal;
        this.searchMealByName = searchMealByName;
        this.filterByMainIngredient = filterByMainIngredient;
        this.getMealDetails = getMealDetails;
        this.filterMealByCategory = filterMealByCategory;
        this.markFavoriteMeal = markFavoriteMeal;
        this.mealModelMapper = mealModelMapper;

        compositeDisposable = new CompositeDisposable();

        latestMeals = new MutableLiveData<>(ViewStateWrapper.loading());
        searchedMeals = new MutableLiveData<>(ViewStateWrapper.success(new ArrayList<>()));
        favoriteMeals = new MutableLiveData<>(ViewStateWrapper.loading());
        mealsFilteredByMainIngredient = new MutableLiveData<>(ViewStateWrapper.loading());
        meal = new MutableLiveData<>(ViewStateWrapper.init());
        mealsFilteredByCategory = new MutableLiveData<>(ViewStateWrapper.loading());
        mealToBeSearched = new MutableLiveData<>();
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
                .doOnSubscribe(disposable -> latestMeals.postValue(ViewStateWrapper.loading()))
                .subscribe(mealModels -> latestMeals.postValue(ViewStateWrapper.success(mealModels)),
                        throwable -> latestMeals.postValue(ViewStateWrapper.error(throwable))));
    }

    public void requestForFavoriteMeals() {
        compositeDisposable.add(getAllFavoriteMeal.execute(null)
                .flatMapObservable(mealEntities -> Observable.fromIterable(mealEntities))
                .map(mealEntity -> mealModelMapper.transform(mealEntity))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> favoriteMeals.postValue(ViewStateWrapper.loading()))
                .subscribe(mealModels -> {
                    favoriteMeals.postValue(ViewStateWrapper.success(mealModels));
                }, throwable -> {
                    favoriteMeals.postValue(ViewStateWrapper.error(throwable));
                }));
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
                .doOnSubscribe(disposable -> searchedMeals.postValue(ViewStateWrapper.loading()))
                .subscribe(mealModels -> searchedMeals.postValue(ViewStateWrapper.success(mealModels)),
                        throwable -> searchedMeals.postValue(ViewStateWrapper.error(throwable)));

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
                .doOnSubscribe(disposable -> mealsFilteredByMainIngredient.postValue(ViewStateWrapper.loading()))
                .subscribe(mealModels -> mealsFilteredByMainIngredient.postValue(ViewStateWrapper.success(mealModels)),
                        throwable -> mealsFilteredByMainIngredient.postValue(ViewStateWrapper.error(throwable))));
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
                .doOnSubscribe(disposable -> meal.postValue(ViewStateWrapper.loading()))
                .subscribe(mealModels -> {
                            meal.postValue(ViewStateWrapper.success(mealModels.get(0)));
                        },
                        throwable -> meal.postValue(ViewStateWrapper.error(throwable))));
    }

    public void filterMealsByCategory(String p0) {
        compositeDisposable.add(filterMealByCategory.execute(FilterMealByCategory.Params.params(p0))
                .zipWith(getAllFavoriteMeal.execute(null), (mealsEntity, mealEntities) -> new Pair<>(mealsEntity, mealEntities))
                .flatMapObservable(mealsEntityListPair -> {
                    List<MealEntity> filteredByMainIngredient = mealsEntityListPair.first.getMeals();
                    List<MealEntity> favorites = mealsEntityListPair.second;

                    return mealModelMapper.transform(markAllFavoriteMeal.execute(MarkAllFavoriteMeal.Params.params(filteredByMainIngredient, favorites)));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mealsFilteredByCategory.postValue(ViewStateWrapper.loading()))
                .subscribe(mealModels -> mealsFilteredByCategory.postValue(ViewStateWrapper.success(mealModels)),
                        throwable -> mealsFilteredByCategory.postValue(ViewStateWrapper.error(throwable))));
    }

    public void setMealIdToBeSearched(String p0) {
        mealToBeSearched.postValue(p0);
    }

    public Completable cancelSearchByName() {
        requestForLatestMeal();
        return Completable.complete();
    }

    public Completable toggleFavoriteOfAMeal(MealModel mealModel) {
        mealModel.setFavorite(!mealModel.isFavorite());

        if (mealModel.isFavorite()) {
            return addFavoriteMeal
                    .execute(AddFavoriteMeal.Params.params(mealModelMapper.transform(mealModel))).subscribeOn(Schedulers.io());
        } else if (!mealModel.isFavorite()) {
            return removeFavoriteMeal
                    .execute(RemoveFavoriteMeal.Params.params(mealModelMapper.transform(mealModel))).subscribeOn(Schedulers.io());
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void syncMeals(MealModel p0, int index) {
        List<MutableLiveData<ViewStateWrapper<List<MealModel>>>> v0 = new ArrayList<>();
        v0.add(mealsFilteredByMainIngredient);
        v0.add(latestMeals);
        v0.add(searchedMeals);
        v0.add(mealsFilteredByCategory);

        if (v0.size() - 1 > index) {
            index++;
            int v1 = index;
            compositeDisposable.add(sync(p0, v0, index, markFavoriteMeal, mealModelMapper).subscribe(() -> {
                syncMeals(p0, v1);
            }, throwable -> syncMeals(p0, v1)));
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
