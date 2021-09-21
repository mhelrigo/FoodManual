package mhelrigo.foodmanual.data.entity.meal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import mhelrigo.foodmanual.domain.entity.meal.MealEntity;

public class MealsApiEntity {
    @SerializedName("meals")
    private List<MealEntity> mealEntities;

    public MealsApiEntity(List<MealEntity> mealEntities) {
        this.mealEntities = mealEntities;
    }

    public List<MealEntity> getMeals() {
        return mealEntities;
    }

    public void setMeals(List<MealEntity> mealEntities) {
        this.mealEntities = mealEntities;
    }
}
