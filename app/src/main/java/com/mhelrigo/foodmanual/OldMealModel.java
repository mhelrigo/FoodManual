package com.mhelrigo.foodmanual;

import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mhelrigo.foodmanual.mapper.MealModelMapper;
import com.mhelrigo.foodmanual.model.meal.MealModel;
import com.mhelrigo.foodmanual.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import mhelrigo.foodmanual.domain.usecase.meal.AddFavoriteMeal;
import mhelrigo.foodmanual.domain.usecase.meal.GetAllFavoriteMeal;
import mhelrigo.foodmanual.domain.usecase.meal.GetMealDetails;
import mhelrigo.foodmanual.domain.usecase.meal.RemoveFavoriteMeal;
import retrofit2.HttpException;

@HiltViewModel
public class OldMealModel extends BaseViewModel {
    private static final String TAG = "MealViewModel";

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    private CompositeDisposable mCompositeDisposable;
    private GetAllFavoriteMeal getAllFavoriteMeal;
    private AddFavoriteMeal addFavoriteMeal;
    private RemoveFavoriteMeal removeFavoriteMeal;
    private GetMealDetails getMealDetails;
    private MealModelMapper mealModelMapper;

    private MutableLiveData<MealModel> mMealMutableLiveData;
    private MutableLiveData<List<MealModel>> mFavoriteMutableLiveData;

    private ObservableBoolean isNoFavorites = new ObservableBoolean(true);

    private String selectedMealId;

    @Inject
    public OldMealModel(GetAllFavoriteMeal getAllFavoriteMeal,
                        AddFavoriteMeal addFavoriteMeal,
                        RemoveFavoriteMeal removeFavoriteMeal,
                        GetMealDetails getMealDetails,
                        MealModelMapper mealModelMapper) {
        mCompositeDisposable = new CompositeDisposable();
        mMealMutableLiveData = new MutableLiveData<>();
        mFavoriteMutableLiveData = new MutableLiveData<>();

        this.getAllFavoriteMeal = getAllFavoriteMeal;
        this.addFavoriteMeal = addFavoriteMeal;
        this.removeFavoriteMeal = removeFavoriteMeal;
        this.getMealDetails = getMealDetails;
        this.mealModelMapper = mealModelMapper;

        fetchFavorites();
    }

    public void fetchMealById(String id) {
        selectedMealId = id;
        mCompositeDisposable.add(getMealDetails.execute(GetMealDetails.Params.params(id))
                .flatMapObservable(meals -> Observable.fromIterable(meals.getMeals()))
                .map(meal -> mealModelMapper.transform(meal))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                    mMealMutableLiveData.postValue(meal);
                }, throwable -> {
                    if (throwable instanceof HttpException) {
                        isRetryNetworkRequest = true;
                    }
                }));
    }

    public void setSelectedMeal(MealModel mealModel) {
        if (mFavoriteMutableLiveData.getValue().contains(mealModel)) {

        }

        if (mealModel != null) {
            firebaseAnalytics.logEvent(Constants.FireBaseAnalyticsEvent.MEAL + mealModel.getStrMeal().replaceAll("\\s", "_"), null);
        }

        mMealMutableLiveData.setValue(mealModel);
    }

    public LiveData<MealModel> getSelectedMeal() {
        return mMealMutableLiveData;
    }

    public void setSelectedMealId(String selectedMealId) {
        this.selectedMealId = selectedMealId;
    }

    public String getSelectedMealId() {
        return selectedMealId;
    }

    public void addToFavorites(MealModel mealModel) {
        saveMeal(mealModel);
        firebaseAnalytics.logEvent(Constants.FireBaseAnalyticsEvent.MEAL_ADDED_TO_FAV + mealModel.getStrMeal().replaceAll("\\s", "_"), null);
    }

    public void removeFromFavorites(MealModel mealModel) {
        deleteMeal(mealModel);
        firebaseAnalytics.logEvent(Constants.FireBaseAnalyticsEvent.MEAL_REMOVED_FROM_FAV + mealModel.getStrMeal().replaceAll("\\s", "_"), null);
    }

    public void saveMeal(MealModel mealModel) {
        mCompositeDisposable.add(
                addFavoriteMeal.execute(AddFavoriteMeal.Params.params(mealModelMapper.transform(mealModel)))
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {
                            Log.e("MealViewModel", "Added to favorites");
                        }));
    }

    public void deleteMeal(MealModel mealModel) {
        mCompositeDisposable.add(
                removeFavoriteMeal.execute(RemoveFavoriteMeal.Params.params(mealModelMapper.transform(mealModel)))
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {
                            Log.e("MealViewModel", "Removed to favorites");
                        }));
    }

    public void fetchFavorites() {
        mCompositeDisposable.add(getAllFavoriteMeal.execute(null)
                .flatMapObservable(meals -> Observable.fromIterable(meals))
                .map(meal -> mealModelMapper.transform(meal))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    mFavoriteMutableLiveData.postValue(meals);
                    if (meals.size() <= 0) {
                        setIsNoFavorites(true);
                        return;
                    }
                    setIsNoFavorites(false);
                })
        );
    }

    public LiveData<List<MealModel>> getFavouriteMeals() {
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
