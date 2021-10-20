package com.mhelrigo.foodmanual.ui.meal;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.mhelrigo.foodmanual.mapper.MealModelMapper;
import com.mhelrigo.foodmanual.model.meal.MealModel;
import com.mhelrigo.foodmanual.ui.commons.base.ViewStateWrapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;
import mhelrigo.foodmanual.domain.usecase.meal.MarkAllFavoriteMeal;
import mhelrigo.foodmanual.domain.usecase.meal.MarkFavoriteMeal;
import timber.log.Timber;

interface SyncMeals {
    /**
     * Modifies given lists of meals when a given meal's [MealModel.isFavorite] changes.
     * It does so by iterating through the list until it matches given meal's [MealModel.idMeal]
     *
     * @param p0 - The Meal
     * @param p1 - The list of Meals
     * @param p2 - Index
     * @param p3 - Use case
     * @param p4 - Mapper
     * @return Boolean - If Drink exist will return True, else will return false.
     */
    default Completable sync(MealModel p0,
                             List<MutableLiveData<ViewStateWrapper<List<MealModel>>>> p1,
                             int p2,
                             MarkFavoriteMeal p3,
                             MealModelMapper p4) {
        final int index = p2;

        if (p1.get(index).getValue().getResult() == null) {
            return Completable.error(new Exception());
        }

        return Observable.fromIterable(p1.get(index).getValue().getResult())
                .filter(v1 -> Integer.parseInt(v1.getIdMeal()) == Integer.parseInt(p0.getIdMeal()))
                .firstOrError()
                .zipWith(Single.just(p1.get(index).getValue().getResult()), (mealModel, mealModels) -> {
                    List<MealEntity> v2 = new ArrayList<>();

                    for (MealModel m : mealModels) {
                        v2.add(p4.transform((MealModel) m.clone()));
                    }

                    return new Pair<>(v2, p4.transform(mealModel));
                })
                .map(v5 -> p4.transform(p3.execute(MarkFavoriteMeal.Params.params(v5.first, v5.second))))
                .flatMap(v6 -> v6)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(v7 -> p1.get(index).postValue(ViewStateWrapper.success(v7))).ignoreElement();
    }
}
