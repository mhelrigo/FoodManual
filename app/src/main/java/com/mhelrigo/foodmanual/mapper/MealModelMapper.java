package com.mhelrigo.foodmanual.mapper;

import com.mhelrigo.foodmanual.model.meal.MealModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;

@Singleton
public class MealModelMapper {
    @Inject
    public MealModelMapper() {
    }

    public MealModel transform(MealEntity mealEntity) {
        return new MealModel(mealEntity.getIdMeal(),
                mealEntity.getStrMeal(),
                mealEntity.getStrDrinkAlternate(),
                mealEntity.getStrCategory(),
                mealEntity.getStrArea(),
                mealEntity.getStrInstructions(),
                mealEntity.getStrMealThumb(),
                mealEntity.getStrTags(),
                mealEntity.getStrYoutube(),
                mealEntity.getStrIngredient1(),
                mealEntity.getStrIngredient2(),
                mealEntity.getStrIngredient3(),
                mealEntity.getStrIngredient4(),
                mealEntity.getStrIngredient5(),
                mealEntity.getStrIngredient6(),
                mealEntity.getStrIngredient7(),
                mealEntity.getStrIngredient8(),
                mealEntity.getStrIngredient9(),
                mealEntity.getStrIngredient10(),
                mealEntity.getStrIngredient11(),
                mealEntity.getStrIngredient12(),
                mealEntity.getStrIngredient13(),
                mealEntity.getStrIngredient14(),
                mealEntity.getStrIngredient15(),
                mealEntity.getStrIngredient16(),
                mealEntity.getStrIngredient17(),
                mealEntity.getStrIngredient18(),
                mealEntity.getStrIngredient19(),
                mealEntity.getStrIngredient20(),
                mealEntity.getStrMeasure1(),
                mealEntity.getStrMeasure2(),
                mealEntity.getStrMeasure3(),
                mealEntity.getStrMeasure4(),
                mealEntity.getStrMeasure5(),
                mealEntity.getStrMeasure6(),
                mealEntity.getStrMeasure7(),
                mealEntity.getStrMeasure8(),
                mealEntity.getStrMeasure9(),
                mealEntity.getStrMeasure10(),
                mealEntity.getStrMeasure11(),
                mealEntity.getStrMeasure12(),
                mealEntity.getStrMeasure13(),
                mealEntity.getStrMeasure14(),
                mealEntity.getStrMeasure15(),
                mealEntity.getStrMeasure16(),
                mealEntity.getStrMeasure17(),
                mealEntity.getStrMeasure18(),
                mealEntity.getStrMeasure19(),
                mealEntity.getStrMeasure20(),
                mealEntity.getStrSource(),
                mealEntity.getDateModified(),
                mealEntity.isFavorite());
    }

    public MealEntity transform(MealModel mealModel) {
        return new MealEntity(mealModel.getIdMeal(),
                mealModel.getStrMeal(),
                mealModel.getStrDrinkAlternate(),
                mealModel.getStrCategory(),
                mealModel.getStrArea(),
                mealModel.getStrInstructions(),
                mealModel.getStrMealThumb(),
                mealModel.getStrTags(),
                mealModel.getStrYoutube(),
                mealModel.getStrIngredient1(),
                mealModel.getStrIngredient2(),
                mealModel.getStrIngredient3(),
                mealModel.getStrIngredient4(),
                mealModel.getStrIngredient5(),
                mealModel.getStrIngredient6(),
                mealModel.getStrIngredient7(),
                mealModel.getStrIngredient8(),
                mealModel.getStrIngredient9(),
                mealModel.getStrIngredient10(),
                mealModel.getStrIngredient11(),
                mealModel.getStrIngredient12(),
                mealModel.getStrIngredient13(),
                mealModel.getStrIngredient14(),
                mealModel.getStrIngredient15(),
                mealModel.getStrIngredient16(),
                mealModel.getStrIngredient17(),
                mealModel.getStrIngredient18(),
                mealModel.getStrIngredient19(),
                mealModel.getStrIngredient20(),
                mealModel.getStrMeasure1(),
                mealModel.getStrMeasure2(),
                mealModel.getStrMeasure3(),
                mealModel.getStrMeasure4(),
                mealModel.getStrMeasure5(),
                mealModel.getStrMeasure6(),
                mealModel.getStrMeasure7(),
                mealModel.getStrMeasure8(),
                mealModel.getStrMeasure9(),
                mealModel.getStrMeasure10(),
                mealModel.getStrMeasure11(),
                mealModel.getStrMeasure12(),
                mealModel.getStrMeasure13(),
                mealModel.getStrMeasure14(),
                mealModel.getStrMeasure15(),
                mealModel.getStrMeasure16(),
                mealModel.getStrMeasure17(),
                mealModel.getStrMeasure18(),
                mealModel.getStrMeasure19(),
                mealModel.getStrMeasure20(),
                mealModel.getStrSource(),
                mealModel.getDateModified(),
                mealModel.isFavorite());
    }

    private List<MealModel> transform(List<MealEntity> p0) {
        List<MealModel> v0 = new ArrayList<>();

        for (MealEntity v1 :
                p0) {
            v0.add(this.transform(v1));
        }

        return v0;
    }

    public Observable<List<MealModel>> transform(Observable<MealEntity> mealObservable) {
        return mealObservable.map(this::transform).toList().toObservable();
    }

    public Single<List<MealModel>> transform(Single<List<MealEntity>> p0) {
        return p0.map(this::transform);
    }
}
