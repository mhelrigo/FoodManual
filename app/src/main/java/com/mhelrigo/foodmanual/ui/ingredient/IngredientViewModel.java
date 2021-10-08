package com.mhelrigo.foodmanual.ui.ingredient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mhelrigo.foodmanual.mapper.IngredientModelMapper;
import com.mhelrigo.foodmanual.model.ingredient.IngredientModel;
import com.mhelrigo.foodmanual.ui.commons.base.ViewStateWrapper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import mhelrigo.foodmanual.domain.usecase.ingredient.GetAllIngredient;

@HiltViewModel
public class IngredientViewModel extends ViewModel {
    private GetAllIngredient getAllIngredient;
    private IngredientModelMapper ingredientModelMapper;

    public CompositeDisposable compositeDisposable;

    private MutableLiveData<ViewStateWrapper<List<IngredientModel>>> ingredients;

    public LiveData<ViewStateWrapper<List<IngredientModel>>> ingredients() {
        return ingredients;
    }

    private MutableLiveData<IngredientModel> ingredient;

    public LiveData<IngredientModel> ingredient() {
        return ingredient;
    }

    @Inject
    public IngredientViewModel(GetAllIngredient getAllIngredient, IngredientModelMapper ingredientModelMapper) {
        this.getAllIngredient = getAllIngredient;
        this.ingredientModelMapper = ingredientModelMapper;

        compositeDisposable = new CompositeDisposable();

        ingredients = new MutableLiveData<>(ViewStateWrapper.loading());
        ingredient = new MutableLiveData<>();
    }

    public void requestForAllIngredient() {
        compositeDisposable.add(getAllIngredient.execute(null)
                .subscribeOn(Schedulers.io())
                .flatMapObservable(ingredientsEntity -> Observable.fromIterable(ingredientsEntity.getIngredientEntity()))
                .map(ingredientEntity -> ingredientModelMapper.transform(ingredientEntity))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> ingredients.postValue(ViewStateWrapper.loading()))
                .subscribe(ingredientModels -> {
                    ingredients.postValue(ViewStateWrapper.success(ingredientModels));
                }, throwable -> {
                    ingredients.postValue(ViewStateWrapper.error(throwable));
                }));
    }

    public void setIngredient(IngredientModel p0) {
        ingredient.postValue(p0);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
