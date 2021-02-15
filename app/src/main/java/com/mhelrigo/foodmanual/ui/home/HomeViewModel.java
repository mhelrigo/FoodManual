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
    private MutableLiveData<Meals> mRandomMealsMutableLiveData;
    protected ObservableBoolean isNoRecords = new ObservableBoolean(false);

    private static final int RANDOM_MEAL_REQUEST = 1;
    private static final int LATEST_MEAL_REQUEST = 2;
    /*private static final int SEARCHED_MEALS_REQUEST = 3;*/

    private int requestRetryType = -1;
    private String searchFilterTemp = "";

    private List<Meal> mealList;
    private MutableLiveData<List<Meal>> mutableLiveDataMeals;


    private int currentMealPage = 1;
    private int currentMealIndex = 0;
    private int pageSize = 10;

    @Inject
    public HomeViewModel(DataRepository dataRepository) {
        this.mDataRepository = dataRepository;
        mCompositeDisposable = new CompositeDisposable();
        mLatestMealsMutableLiveData = new MutableLiveData<>();
        mRandomMealsMutableLiveData = new MutableLiveData<>();
        mutableLiveDataMeals = new MutableLiveData<>();
        mealList = new ArrayList<>();
    }

    public void fetchMeals() {
        Log.e(TAG, "Meals Size : " + mealList.size() + " currentMealIndex : " + currentMealIndex);
        if (currentMealIndex >= mealList.size()) {
            fetchRandomMeal();
            return;
        }

        List<Meal> meals1 = new ArrayList<>();
        currentMealIndex = (currentMealPage - 1) * pageSize;
        for (int i = currentMealIndex; i < Math.min(currentMealIndex + pageSize, mealList.size()); i++) {
            meals1.add(mealList.get(i));
        }
        currentMealPage++;
        mutableLiveDataMeals.postValue(meals1);
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
                }, throwable -> {
                    Log.e(TAG, throwable.getMessage());
                    if (throwable instanceof HttpException) {
                        isRetryNetworkRequest = true;
                        requestRetryType = LATEST_MEAL_REQUEST;
                    }
                }));
    }

    public void fetchRandomMeal() {
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
                    mRandomMealsMutableLiveData.postValue(meals);
                }, throwable -> {
                    if (throwable instanceof HttpException) {
                        isRetryNetworkRequest = true;
                        requestRetryType = RANDOM_MEAL_REQUEST;
                    }
                }));
    }

    /*public void fetchMealByCharacters(String filters) {
        setIsNoRecords(false);

        if (filters.equals("") || filters.equals(" ")) {
            fetchLatestMeals();

            Log.e(TAG, "fetchMealByCharacters null");
            return;
        }

        searchFilterTemp = filters;

        mCompositeDisposable.clear();
        mCompositeDisposable.add(mDataRepository.fetchMealsByCharacter(searchFilterTemp).zipWith(mDataRepository.fetchFavorites(), (meals, meals2) -> {
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
                    Log.e(TAG, "fetchMealByCharacters : " + meals.getMealList().size());
                    *//*mLatestMealsMutableLiveData.postValue(meals);*//*
                    mealList = meals.getMealList();
                    fetchMeals();
                }, throwable -> {
                    Log.e(TAG, "fetchMealByCharacters: " + throwable.getMessage());
                    if (throwable instanceof HttpException) {
                        isRetryNetworkRequest = true;
                        requestRetryType = SEARCHED_MEALS_REQUEST;
                    }
                }));
    }*/

    public LiveData<Meals> getMealsData() {
        return mLatestMealsMutableLiveData;
    }

    public LiveData<Meals> getRandomMeals() {
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

    public LiveData<List<Meal>> getMeals() {
        return mutableLiveDataMeals;
    }

    public ObservableBoolean getIsNoRecords() {
        return isNoRecords;
    }

    @Override
    protected void onRetryNetworkRequests() {
        if (isRetryNetworkRequest && isNetworkConnected.get()) {
            if (requestRetryType == RANDOM_MEAL_REQUEST) {
                fetchRandomMeal();
            } else if (requestRetryType == LATEST_MEAL_REQUEST) {
                fetchLatestMeals();
            } /*else if (requestRetryType == SEARCHED_MEALS_REQUEST) {
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
