package mhelrigo.foodmanual.domain.entity.meal;

import java.util.List;

public class MealsEntity {
    private List<MealEntity> mealEntities;

    public MealsEntity(List<MealEntity> mealEntities) {
        this.mealEntities = mealEntities;
    }

    public List<MealEntity> getMeals() {
        return mealEntities;
    }

    public void setMeals(List<MealEntity> mealEntities) {
        this.mealEntities = mealEntities;
    }
}
