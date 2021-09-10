package com.mhelrigo.foodmanual.ui.home;

import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.common.collect.Lists;
import com.mhelrigo.foodmanual.BaseViewModel;
import com.mhelrigo.foodmanual.data.DataRepository;
import com.mhelrigo.foodmanual.data.model.Meal;
import com.mhelrigo.foodmanual.data.model.api.Meals;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

@Singleton
public class HomeViewModel extends BaseViewModel {
    private static final String TAG = "HomeViewModel";

    private DataRepository mDataRepository;
    private CompositeDisposable mCompositeDisposable;
    private MutableLiveData<Meals> mLatestMealsMutableLiveData;
    private MutableLiveData<Meals> mLatestMealsPaginationMutableLiveData;
    private MutableLiveData<Meals> mRandomMealsMutableLiveData;
    protected ObservableBoolean isNoRecords = new ObservableBoolean(false);

    private static final int RANDOM_MEAL_REQUEST = 1;
    private static final int LATEST_MEAL_REQUEST = 2;
    private static final int RANDOM_MEAL_PAGINATION_REQUEST = 3;
    /*private static final int SEARCHED_MEALS_REQUEST = 3;*/

    private int requestRetryType = -1;

    @Inject
    public HomeViewModel(DataRepository dataRepository) {
        this.mDataRepository = dataRepository;
        mCompositeDisposable = new CompositeDisposable();
        mLatestMealsMutableLiveData = new MutableLiveData<>();
        mLatestMealsPaginationMutableLiveData = new MutableLiveData<>();
        mRandomMealsMutableLiveData = new MutableLiveData<>();
    }

    public void fetchLatestMeals() {
        Log.e(TAG, "fetchLatestMeals");
        mCompositeDisposable.add(mDataRepository.fetchLatestMeals().zipWith(mDataRepository.fetchFavorites(), (meals, meals2) -> {
            if (meals2.size() == 0) {
                return meals;
            }

            for (int index = 0; index <= meals.getMealList().size() - 1; index++) {
                for (Meal favorites : meals2) {
                    if (meals.getMealList().get(index).getIdMeal().equals(favorites.getIdMeal())) {
                        meals.getMealList().get(index).setFavorite(true);
                    }
                }
            }

            return meals;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(meals -> {
                    mLatestMealsMutableLiveData.postValue(meals);
                    setIsNoRecords(false);
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
        Log.e(TAG, "fetchRandomMeal");
        mCompositeDisposable.add(mDataRepository.fetchRandomMeals().zipWith(mDataRepository.fetchFavorites(), (meals, meals2) -> {
            if (meals2.size() == 0) {
                return meals;
            }

            for (int index = 0; index <= meals.getMealList().size() - 1; index++) {
                for (Meal favorites : meals2) {
                    if (meals.getMealList().get(index).getIdMeal().equals(favorites.getIdMeal())) {
                        meals.getMealList().get(index).setFavorite(true);
                    }
                }
            }

            return meals;
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    if (isPagination) {
                        mLatestMealsPaginationMutableLiveData.postValue(meals);
                    } else {
                        mRandomMealsMutableLiveData.postValue(meals);
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

    public void manageFavorite(Meal meal, boolean isFavorite) {
        if (mLatestMealsMutableLiveData.getValue().getMealList().contains(meal)) {
            mLatestMealsMutableLiveData.getValue().getMealList().get(mLatestMealsMutableLiveData.getValue().getMealList().indexOf(meal)).setFavorite(isFavorite);
            setLatestMeal(mLatestMealsMutableLiveData.getValue());
        }

        if (mRandomMealsMutableLiveData.getValue().getMealList().contains(meal)) {
            mRandomMealsMutableLiveData.getValue().getMealList().get(mRandomMealsMutableLiveData.getValue().getMealList().indexOf(meal)).setFavorite(isFavorite);
            setLatestMeal(mRandomMealsMutableLiveData.getValue());
        }
    }

    public LiveData<Meals> getMealsData() {
        return mLatestMealsMutableLiveData;
    }

    public LiveData<Meals> getRandomMeals(boolean isPagination) {
        if (isPagination) {
            return mLatestMealsPaginationMutableLiveData;
        }
        return mRandomMealsMutableLiveData;
    }

    public void setLatestMeal(Meals meals) {
        mLatestMealsMutableLiveData.setValue(meals);
    }

    public void setRandomMeals(Meals meals) {
        mRandomMealsMutableLiveData.setValue(meals);
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
            /*else if (requestRetryType == SEARCHED_MEALS_REQUEST) {
                fetchMealByCharacters(searchFilterTemp);
            }*/

            requestRetryType = -1;
        }
        super.onRetryNetworkRequests();
    }

    public static <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {
        if (pageSize <= 0 || page <= 0) {
            throw new IllegalArgumentException("invalid page size: " + pageSize);
        }

        int fromIndex = (page - 1) * pageSize;
        if (sourceList == null || sourceList.size() <= fromIndex) {
            return Collections.emptyList();
        }

        // toIndex exclusive
        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }
}
