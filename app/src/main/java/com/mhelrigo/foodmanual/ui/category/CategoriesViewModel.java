package com.mhelrigo.foodmanual.ui.category;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mhelrigo.foodmanual.BaseViewModel;
import com.mhelrigo.foodmanual.data.DataRepository;
import com.mhelrigo.foodmanual.data.model.Category;
import com.mhelrigo.foodmanual.data.model.api.Categories;
import com.mhelrigo.foodmanual.data.model.api.Meals;
import com.mhelrigo.foodmanual.utils.Constants;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

@HiltViewModel
public class CategoriesViewModel extends BaseViewModel {
    private static final String TAG = "CategoriesViewModel";

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    private DataRepository mDataRepository;
    private CompositeDisposable mCompositeDisposable;

    private MutableLiveData<Categories> mCategoriesMutableLiveData;
    private MutableLiveData<Category> mCategoryMutableLiveData;
    private MutableLiveData<Meals> mMealsMutableLiveData;

    private static final int CATEGORY_REQUEST = 1;
    private static final int CATEGORY_MEALS_REQUEST = 2;
    public static final int  BOTH_REQUEST = 3;

    private int requestRetryType = -1;

    @Inject
    public CategoriesViewModel(DataRepository dataRepository) {
        this.mDataRepository = dataRepository;
        mCategoriesMutableLiveData = new MutableLiveData<>();
        mCategoryMutableLiveData = new MutableLiveData<>();
        mMealsMutableLiveData = new MutableLiveData<>();
        mCompositeDisposable = new CompositeDisposable();
    }

    public void reset() {
        mMealsMutableLiveData.setValue(null);
        mCompositeDisposable.clear();
    }

    private void retryRequestManager(int requestRetryType){
        if (requestRetryType > -1){
            requestRetryType = BOTH_REQUEST;
        }

        this.requestRetryType = requestRetryType;
    }

    public void fetchCategories() {
        Log.e(TAG, "fetchCategories...");
        mCompositeDisposable.add(mDataRepository
                .fetchCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categories -> {
                    mCategoriesMutableLiveData.setValue(categories);
                }, throwable -> {
                    if (throwable instanceof HttpException) {
                        isRetryNetworkRequest = true;
                        retryRequestManager(CATEGORY_REQUEST);
                    }
                }));
    }

    public LiveData<Categories> getCategories() {
        return mCategoriesMutableLiveData;
    }

    public void setSelectedCategory(Category category) {
        firebaseAnalytics.logEvent(Constants.FireBaseAnalyticsEvent.MEAL_CATEGORY + category.getStrCategory().replaceAll("\\s", "_"), null);
        mCategoryMutableLiveData.setValue(category);
    }

    public LiveData<Category> getSelectedCategory() {
        return mCategoryMutableLiveData;
    }

    public void fetchMealsByCategory() {
        Log.e(TAG, "fetchMealsByCategory...");
        mCompositeDisposable.add(mDataRepository
                .fetchMealsFilteredByCategory(mCategoryMutableLiveData.getValue().getStrCategory())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    mMealsMutableLiveData.setValue(meals);
                }, throwable -> {
                    if (throwable instanceof HttpException) {
                        isRetryNetworkRequest = true;
                        retryRequestManager(CATEGORY_MEALS_REQUEST);
                    }
                }));
    }

    public LiveData<Meals> getMealsByCategory() {
        return mMealsMutableLiveData;
    }

    @Override
    protected void onRetryNetworkRequests() {
        Log.e(TAG, "onRetryNetworkRequests...");
        if (isRetryNetworkRequest && isNetworkConnected.get()) {
            if (requestRetryType == CATEGORY_REQUEST) {
                fetchCategories();
            } else if (requestRetryType == CATEGORY_MEALS_REQUEST) {
                fetchMealsByCategory();
            } else if (requestRetryType == BOTH_REQUEST){
                fetchCategories();
                fetchMealsByCategory();
            }

            requestRetryType = -1;
        }
        super.onRetryNetworkRequests();
    }
}
