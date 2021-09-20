package com.mhelrigo.foodmanual.ui.category;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mhelrigo.foodmanual.BaseViewModel;
import com.mhelrigo.foodmanual.mapper.CategoryModelMapper;
import com.mhelrigo.foodmanual.mapper.MealModelMapper;
import com.mhelrigo.foodmanual.model.category.CategoryModel;
import com.mhelrigo.foodmanual.model.meal.MealModel;
import com.mhelrigo.foodmanual.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import mhelrigo.foodmanual.domain.usecase.category.GetAllCategory;
import mhelrigo.foodmanual.domain.usecase.meal.SearchMealByCategory;
import retrofit2.HttpException;

@HiltViewModel
public class CategoriesViewModel extends BaseViewModel {
    private static final String TAG = "CategoriesViewModel";

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    private GetAllCategory getAllCategory;
    private SearchMealByCategory searchMealByCategory;
    private CategoryModelMapper categoryModelMapper;
    private MealModelMapper mealModelMapper;
    private CompositeDisposable mCompositeDisposable;

    private MutableLiveData<List<CategoryModel>> mCategoriesMutableLiveData;
    private MutableLiveData<CategoryModel> mCategoryMutableLiveData;
    private MutableLiveData<List<MealModel>> mMealsMutableLiveData;

    private static final int CATEGORY_REQUEST = 1;
    private static final int CATEGORY_MEALS_REQUEST = 2;
    public static final int BOTH_REQUEST = 3;

    private int requestRetryType = -1;

    @Inject
    public CategoriesViewModel(GetAllCategory getAllCategory,
                               SearchMealByCategory searchMealByCategory,
                               CategoryModelMapper categoryModelMapper,
                               MealModelMapper mealModelMapper) {
        this.getAllCategory = getAllCategory;
        this.searchMealByCategory = searchMealByCategory;
        this.categoryModelMapper = categoryModelMapper;
        this.mealModelMapper = mealModelMapper;

        mCategoriesMutableLiveData = new MutableLiveData<>();
        mCategoryMutableLiveData = new MutableLiveData<>();
        mMealsMutableLiveData = new MutableLiveData<>();
        mCompositeDisposable = new CompositeDisposable();
    }

    public void reset() {
        mMealsMutableLiveData.setValue(null);
        mCompositeDisposable.clear();
    }

    private void retryRequestManager(int requestRetryType) {
        if (requestRetryType > -1) {
            requestRetryType = BOTH_REQUEST;
        }

        this.requestRetryType = requestRetryType;
    }

    public void fetchCategories() {
        Log.e(TAG, "fetchCategories...");
        mCompositeDisposable.add(getAllCategory.execute(null)
                .flatMapObservable(categories -> categoryModelMapper.transform(categories.getCategories()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categories -> {
                    mCategoriesMutableLiveData.postValue(categories);
                }, throwable -> {
                    Log.e("throwable", throwable.getMessage());
                    if (throwable instanceof HttpException) {
                        isRetryNetworkRequest = true;
                        retryRequestManager(CATEGORY_REQUEST);
                    }
                }));
    }

    public LiveData<List<CategoryModel>> getCategories() {
        return mCategoriesMutableLiveData;
    }

    public void setSelectedCategory(CategoryModel categoryModel) {
        firebaseAnalytics.logEvent(Constants.FireBaseAnalyticsEvent.MEAL_CATEGORY + categoryModel.getStrCategory().replaceAll("\\s", "_"), null);
        mCategoryMutableLiveData.postValue(categoryModel);
    }

    public LiveData<CategoryModel> getSelectedCategory() {
        return mCategoryMutableLiveData;
    }

    public void fetchMealsByCategory() {
        Log.e(TAG, "fetchMealsByCategory...");
        mCompositeDisposable.add(searchMealByCategory.execute(SearchMealByCategory.Params.params(mCategoryMutableLiveData.getValue().getStrCategory()))
                .flatMapObservable(meals -> Observable.fromIterable(meals.getMeals()))
                .map(meal -> mealModelMapper.transform(meal))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meals -> {
                    mMealsMutableLiveData.postValue(meals);
                }, throwable -> {
                    if (throwable instanceof HttpException) {
                        isRetryNetworkRequest = true;
                        retryRequestManager(CATEGORY_MEALS_REQUEST);
                    }
                }));
    }

    public LiveData<List<MealModel>> getMealsByCategory() {
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
            } else if (requestRetryType == BOTH_REQUEST) {
                fetchCategories();
                fetchMealsByCategory();
            }

            requestRetryType = -1;
        }
        super.onRetryNetworkRequests();
    }
}
