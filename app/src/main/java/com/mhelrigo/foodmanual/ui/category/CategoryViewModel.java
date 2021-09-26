package com.mhelrigo.foodmanual.ui.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mhelrigo.foodmanual.mapper.CategoryModelMapper;
import com.mhelrigo.foodmanual.model.category.CategoryModel;
import com.mhelrigo.foodmanual.ui.base.ResultWrapper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import mhelrigo.foodmanual.domain.usecase.category.GetAllCategory;

@HiltViewModel
public class CategoryViewModel extends ViewModel {
    private GetAllCategory getAllCategory;
    private CategoryModelMapper categoryModelMapper;

    public CompositeDisposable compositeDisposable;

    private MutableLiveData<ResultWrapper<List<CategoryModel>>> categories;

    public LiveData<ResultWrapper<List<CategoryModel>>> categories() {
        return categories;
    }

    private MutableLiveData<CategoryModel> category;

    public LiveData<CategoryModel> category() {
        return category;
    }

    @Inject
    public CategoryViewModel(GetAllCategory getAllCategory) {
        this.getAllCategory = getAllCategory;

        categoryModelMapper = new CategoryModelMapper();
        compositeDisposable = new CompositeDisposable();

        categories = new MutableLiveData<>();
        category = new MutableLiveData<>();
    }

    public void requestForCategories() {
        compositeDisposable.add(getAllCategory.execute(null)
                .flatMapObservable(categoriesEntity -> Observable.fromIterable(categoriesEntity.getCategories()))
                .map(categoryEntity -> categoryModelMapper.transform(categoryEntity))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> categories.postValue(ResultWrapper.loading()))
                .doOnError(throwable -> categories.postValue(ResultWrapper.error(throwable)))
                .subscribe(categoryModels -> categories.postValue(ResultWrapper.success(categoryModels))));
    }

    public void setCategory(CategoryModel p0) {
        category.postValue(p0);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
