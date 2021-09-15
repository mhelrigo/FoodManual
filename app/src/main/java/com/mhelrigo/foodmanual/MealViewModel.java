package com.mhelrigo.foodmanual;

import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mhelrigo.foodmanual.data.DataRepository;
import com.mhelrigo.foodmanual.data.model.Meal;
import com.mhelrigo.foodmanual.data.model.api.Meals;
import com.mhelrigo.foodmanual.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

@Singleton
public class MealViewModel extends BaseViewModel {
    private static final String TAG = "MealViewModel";

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    private DataRepository mDataRepository;
    private CompositeDisposable mCompositeDisposable;

    private MutableLiveData<Meal> mMealMutableLiveData;
    private MutableLiveData<List<Meal>> mFavoriteMutableLiveData;

    private ObservableBoolean isNoFavorites = new ObservableBoolean(true);

    private String selectedMealId;

    @Inject
    public MealViewModel(DataRepository mDataRepository) {
        this.mDataRepository = mDataRepository;
        mCompositeDisposable = new CompositeDisposable();
        mMealMutableLiveData = new MutableLiveData<>();
        mFavoriteMutableLiveData = new MutableLiveData<>();

        fetchFavorites();
    }

    public void fetchMealById(String id) {
        Log.e(TAG, "fetchMealById");
        selectedMealId = id;
        mCompositeDisposable.add(mDataRepository.fetchMealById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    mMealMutableLiveData.postValue(meals.getMealList().get(0));
                }, throwable -> {
                    Log.e(TAG, "throwable : " + throwable.getMessage());
                    if (throwable instanceof HttpException) {
                        isRetryNetworkRequest = true;
                    }
                }));
    }

    public void setSelectedMeal(Meal meal) {
        if (mFavoriteMutableLiveData.getValue().contains(meal)) {

        }

        if (meal != null) {
            firebaseAnalytics.logEvent(Constants.FireBaseAnalyticsEvent.MEAL + meal.getStrMeal().replaceAll("\\s", "_"), null);
        }

        mMealMutableLiveData.setValue(meal);
    }

    public LiveData<Meal> getSelectedMeal() {
        return mMealMutableLiveData;
    }

    public void setSelectedMealId(String selectedMealId) {
        this.selectedMealId = selectedMealId;
    }

    public String getSelectedMealId() {
        return selectedMealId;
    }

    public void addToFavorites(Meal meal) {
        saveMeal(meal);
        firebaseAnalytics.logEvent(Constants.FireBaseAnalyticsEvent.MEAL_ADDED_TO_FAV + meal.getStrMeal().replaceAll("\\s", "_"), null);
    }

    public void removeFromFavorites(Meal meal) {
        deleteMeal(meal);
        firebaseAnalytics.logEvent(Constants.FireBaseAnalyticsEvent.MEAL_REMOVED_FROM_FAV + meal.getStrMeal().replaceAll("\\s", "_"), null);
    }

    public void saveMeal(Meal meal) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mDataRepository.saveMeal(meal);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError", e);
            }
        });
    }

    public void deleteMeal(Meal meal) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                Log.e(TAG, "meal to be deleted : " + meal.getStrMeal());
                mDataRepository.deleteMeal(meal);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }

    public void fetchFavorites() {
        mCompositeDisposable.add(mDataRepository.fetchFavorites()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(meals -> {
                    mFavoriteMutableLiveData.postValue(meals);
                    Log.e(TAG, "meals.size()" + meals.size());
                    if (meals.size() <= 0) {
                        setIsNoFavorites(true);
                        return;
                    }
                    setIsNoFavorites(false);
                })
        );
    }

    public LiveData<List<Meal>> getFavouriteMeals() {
        return mFavoriteMutableLiveData;
    }

    public void setIsNoFavorites(boolean isNoFavorites) {
        this.isNoFavorites.set(isNoFavorites);
    }

    public ObservableBoolean getIsNoFavorites() {
        return isNoFavorites;
    }

    @Override
    public void onRetryNetworkRequests() {
        if (isRetryNetworkRequest && isNetworkConnected.get()) {
            Log.e(TAG, "onRetryNetworkRequests...");
            fetchMealById(selectedMealId);
        }
        super.onRetryNetworkRequests();
    }
}
