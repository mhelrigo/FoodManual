package com.mhelrigo.foodmanual.ui.meal;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mhelrigo.foodmanual.mapper.MealModelMapper;
import com.mhelrigo.foodmanual.model.meal.MealModel;
import com.mhelrigo.foodmanual.ui.base.ResultWrapper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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
    private FilterMealByCategory filterMealByCategory;
    private MealModelMapper mealModelMapper;

    public CompositeDisposable compositeDisposable;

    private MutableLiveData<ResultWrapper<List<MealModel>>> latestMeals;

    public LiveData<ResultWrapper<List<MealModel>>> latestMeals() {
        return latestMeals;
    }

    private MutableLiveData<ResultWrapper<List<MealModel>>> searchedMeals;

    public LiveData<ResultWrapper<List<MealModel>>> searchedMeals() {
        return searchedMeals;
    }

    private MutableLiveData<ResultWrapper<List<MealModel>>> favoriteMeals;

    public LiveData<ResultWrapper<List<MealModel>>> favoriteMeals() {
        return favoriteMeals;
    }

    private MutableLiveData<ResultWrapper<List<MealModel>>> mealsFilteredByMainIngredient;

    public LiveData<ResultWrapper<List<MealModel>>> mealsFilteredByMainIngredient() {
        return mealsFilteredByMainIngredient;
    }

    private MutableLiveData<ResultWrapper<MealModel>> meal;

    public LiveData<ResultWrapper<MealModel>> meal() {
        return meal;
    }

    private MutableLiveData<ResultWrapper<List<MealModel>>> mealsFilteredByCategory;

    public LiveData<ResultWrapper<List<MealModel>>> mealsFilteredByCategory() {
        return mealsFilteredByCategory;
    }

    private MutableLiveData<String> mealToBeSearched;

    public LiveData<String> mealIdToBeSearched() {
        return mealToBeSearched;
    }

    public PublishSubject<MealModel> mealThatIsToggled = PublishSubject.create();

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
        this.mealModelMapper = mealModelMapper;

        compositeDisposable = new CompositeDisposable();

        latestMeals = new MutableLiveData<>();
        searchedMeals = new MutableLiveData<>();
        favoriteMeals = new MutableLiveData<>();
        mealsFilteredByMainIngredient = new MutableLiveData<>();
        meal = new MutableLiveData<>();
        mealsFilteredByCategory = new MutableLiveData<>();
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
                .doOnSubscribe(disposable -> latestMeals.postValue(ResultWrapper.loading()))
                .subscribe(mealModels -> latestMeals.postValue(ResultWrapper.success(mealModels)),
                        throwable -> latestMeals.postValue(ResultWrapper.error(throwable))));
    }

    public void requestForFavoriteMeals() {
        compositeDisposable.add(getAllFavoriteMeal.execute(null)
                .flatMapObservable(mealEntities -> Observable.fromIterable(mealEntities))
                .map(mealEntity -> mealModelMapper.transform(mealEntity))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> favoriteMeals.postValue(ResultWrapper.loading()))
                .subscribe(mealModels -> {
                    favoriteMeals.postValue(ResultWrapper.success(mealModels));
                }, throwable -> {
                    favoriteMeals.postValue(ResultWrapper.error(throwable));
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
                .doOnSubscribe(disposable -> searchedMeals.postValue(ResultWrapper.loading()))
                .subscribe(mealModels -> searchedMeals.postValue(ResultWrapper.success(mealModels)),
                        throwable -> searchedMeals.postValue(ResultWrapper.error(throwable)));

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
                .doOnSubscribe(disposable -> mealsFilteredByMainIngredient.postValue(ResultWrapper.loading()))
                .subscribe(mealModels -> mealsFilteredByMainIngredient.postValue(ResultWrapper.success(mealModels)),
                        throwable -> mealsFilteredByMainIngredient.postValue(ResultWrapper.error(throwable))));
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
                .doOnSubscribe(disposable -> meal.postValue(ResultWrapper.loading()))
                .subscribe(mealModels -> {
                            meal.postValue(ResultWrapper.success(mealModels.get(0)));
                            Timber.e("SUCCESS : " + mealModels.get(0).getStrInstructions());
                        },
                        throwable -> meal.postValue(ResultWrapper.error(throwable))));
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
                .doOnSubscribe(disposable -> mealsFilteredByCategory.postValue(ResultWrapper.loading()))
                .subscribe(mealModels -> mealsFilteredByCategory.postValue(ResultWrapper.success(mealModels)),
                        throwable -> mealsFilteredByCategory.postValue(ResultWrapper.error(throwable))));
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

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
