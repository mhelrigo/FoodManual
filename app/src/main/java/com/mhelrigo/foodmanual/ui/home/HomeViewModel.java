package com.mhelrigo.foodmanual.ui.home;

import android.util.Log;
import android.util.Pair;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mhelrigo.foodmanual.BaseViewModel;
import com.mhelrigo.foodmanual.mapper.MealModelMapper;
import com.mhelrigo.foodmanual.model.meal.MealModel;
import com.mhelrigo.foodmanual.model.meal.MealsModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;
import mhelrigo.foodmanual.domain.entity.meal.MealsEntity;
import mhelrigo.foodmanual.domain.usecase.meal.GetAllFavoriteMeal;
import mhelrigo.foodmanual.domain.usecase.meal.GetLatestMeals;
import mhelrigo.foodmanual.domain.usecase.meal.GetRandomMeals;
import mhelrigo.foodmanual.domain.usecase.meal.MarkAllFavoriteMeal;
import retrofit2.HttpException;

@HiltViewModel
public class HomeViewModel extends BaseViewModel {
    private static final String TAG = "HomeViewModel";

    private CompositeDisposable compositeDisposable;
    private GetLatestMeals getLatestMeals;
    private GetRandomMeals getRandomMeals;
    private GetAllFavoriteMeal getAllFavoriteMeal;
    private MarkAllFavoriteMeal markAllFavoriteMeal;
    private MealModelMapper mealModelMapper;

    private MutableLiveData<MealsModel> mLatestMealsMutableLiveData;
    private MutableLiveData<MealsModel> mLatestMealsPaginationMutableLiveData;
    private MutableLiveData<MealsModel> mRandomMealsMutableLiveData;
    protected ObservableBoolean isNoRecords = new ObservableBoolean(false);

    private static final int RANDOM_MEAL_REQUEST = 1;
    private static final int LATEST_MEAL_REQUEST = 2;
    private static final int RANDOM_MEAL_PAGINATION_REQUEST = 3;

    private int requestRetryType = -1;

    @Inject
    public HomeViewModel(GetLatestMeals getLatestMeals, GetRandomMeals getRandomMeals, GetAllFavoriteMeal getAllFavoriteMeal, MarkAllFavoriteMeal markAllFavoriteMeal, MealModelMapper mealModelMapper) {
        compositeDisposable = new CompositeDisposable();
        mLatestMealsMutableLiveData = new MutableLiveData<>();
        mLatestMealsPaginationMutableLiveData = new MutableLiveData<>();
        mRandomMealsMutableLiveData = new MutableLiveData<>();

        this.getLatestMeals = getLatestMeals;
        this.mealModelMapper = mealModelMapper;
        this.getRandomMeals = getRandomMeals;
        this.getAllFavoriteMeal = getAllFavoriteMeal;
        this.markAllFavoriteMeal = markAllFavoriteMeal;
    }

    public void fetchLatestMeals() {
        compositeDisposable.add(getLatestMeals.execute(null)
                .zipWith(getAllFavoriteMeal.execute(null), (meals, favorites) -> new Pair<>(meals, favorites))
                .flatMapObservable((Function<Pair<MealsEntity, List<MealEntity>>, Observable<List<MealModel>>>) mealsListPair -> {
                    List<MealEntity> latests = mealsListPair.first.getMeals();
                    List<MealEntity> favorites = mealsListPair.second;

                    return mealModelMapper.transform(markAllFavoriteMeal.execute(MarkAllFavoriteMeal.Params.params(latests, favorites)));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    mLatestMealsMutableLiveData.postValue(new MealsModel(meals));
                }, throwable -> {
                    Log.e(TAG, throwable.getMessage());
                    if (throwable instanceof HttpException) {
                        isRetryNetworkRequest = true;
                        requestRetryType = LATEST_MEAL_REQUEST;
                        return;
                    }

                    isNoRecords.set(true);
                }));
    }

    public void fetchRandomMeal(boolean isPagination) {
        compositeDisposable.add(getRandomMeals.execute(null)
                .zipWith(getAllFavoriteMeal.execute(null), (meals, favorites) -> new Pair<>(meals, favorites))
                .flatMapObservable((Function<Pair<MealsEntity, List<MealEntity>>, Observable<List<MealModel>>>) mealsListPair -> {
                    List<MealEntity> latests = mealsListPair.first.getMeals();
                    List<MealEntity> favorites = mealsListPair.second;

                    return mealModelMapper.transform(markAllFavoriteMeal.execute(MarkAllFavoriteMeal.Params.params(latests, favorites)));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    if (isPagination) {
                        mLatestMealsPaginationMutableLiveData.postValue(new MealsModel(meals));
                    } else {
                        mRandomMealsMutableLiveData.postValue(new MealsModel(meals));
                    }
                }, throwable -> {
                    if (throwable instanceof HttpException) {
                        isRetryNetworkRequest = true;
                        if (isPagination) {
                            requestRetryType = RANDOM_MEAL_PAGINATION_REQUEST;
                        } else {
                            requestRetryType = RANDOM_MEAL_REQUEST;
                        }
                        return;
                    }
                }));
    }

    public void manageFavorite(MealModel mealModel, boolean isFavorite) {
        if (mLatestMealsMutableLiveData.getValue().getMealList().contains(mealModel)) {
            mLatestMealsMutableLiveData.getValue().getMealList().get(mLatestMealsMutableLiveData.getValue().getMealList().indexOf(mealModel)).setFavorite(isFavorite);
            setLatestMeal(mLatestMealsMutableLiveData.getValue());
        }

        if (mRandomMealsMutableLiveData.getValue().getMealList().contains(mealModel)) {
            mRandomMealsMutableLiveData.getValue().getMealList().get(mRandomMealsMutableLiveData.getValue().getMealList().indexOf(mealModel)).setFavorite(isFavorite);
            setLatestMeal(mRandomMealsMutableLiveData.getValue());
        }
    }

    public LiveData<MealsModel> getMealsData() {
        return mLatestMealsMutableLiveData;
    }

    public LiveData<MealsModel> getRandomMeals(boolean isPagination) {
        if (isPagination) {
            return mLatestMealsPaginationMutableLiveData;
        }
        return mRandomMealsMutableLiveData;
    }

    public void setLatestMeal(MealsModel mealsModel) {
        mLatestMealsMutableLiveData.setValue(mealsModel);
    }

    public void setRandomMeals(MealsModel mealsModel) {
        mRandomMealsMutableLiveData.setValue(mealsModel);
    }

    public void setIsNoRecords(boolean isNoRecords) {
        this.isNoRecords.set(isNoRecords);
    }

    public ObservableBoolean getIsNoRecords() {
        return isNoRecords;
    }

    @Override
    protected void onRetryNetworkRequests() {
        if (isRetryNetworkRequest && isNetworkConnected.get()) {
            if (requestRetryType == RANDOM_MEAL_REQUEST) {
                fetchRandomMeal(false);
            } else if (requestRetryType == LATEST_MEAL_REQUEST) {
                fetchLatestMeals();
            } else if (requestRetryType == RANDOM_MEAL_PAGINATION_REQUEST) {
                fetchRandomMeal(true);
            }

            requestRetryType = -1;
        }
        super.onRetryNetworkRequests();
    }
}
