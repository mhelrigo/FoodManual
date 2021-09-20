package com.mhelrigo.foodmanual.model.meal;

import java.util.List;

public class MealsModel {
    private List<MealModel> mealModels;

    public MealsModel(List<MealModel> mealModelList) {
        this.mealModels = mealModelList;
    }

    public List<MealModel> getMealList() {
        return mealModels;
    }

    public void setMealList(List<MealModel> mealModelList) {
        this.mealModels = mealModelList;
    }
}
