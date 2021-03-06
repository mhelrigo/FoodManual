package mhelrigo.foodmanual.data.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import mhelrigo.foodmanual.data.entity.meal.MealDatabaseEntity;
import mhelrigo.foodmanual.data.entity.meal.MealsApiEntity;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;
import mhelrigo.foodmanual.domain.entity.meal.MealsEntity;

@Singleton
public class MealMapper {
    @Inject
    public MealMapper() {
    }

    public MealDatabaseEntity transform(MealEntity mealEntity) {
        return new MealDatabaseEntity(mealEntity.getIdMeal(),
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

    public MealEntity transform(MealDatabaseEntity mealDatabaseEntity) {
        return new MealEntity(mealDatabaseEntity.getIdMeal(),
                mealDatabaseEntity.getStrMeal(),
                mealDatabaseEntity.getStrDrinkAlternate(),
                mealDatabaseEntity.getStrCategory(),
                mealDatabaseEntity.getStrArea(),
                mealDatabaseEntity.getStrInstructions(),
                mealDatabaseEntity.getStrMealThumb(),
                mealDatabaseEntity.getStrTags(),
                mealDatabaseEntity.getStrYoutube(),
                mealDatabaseEntity.getStrIngredient1(),
                mealDatabaseEntity.getStrIngredient2(),
                mealDatabaseEntity.getStrIngredient3(),
                mealDatabaseEntity.getStrIngredient4(),
                mealDatabaseEntity.getStrIngredient5(),
                mealDatabaseEntity.getStrIngredient6(),
                mealDatabaseEntity.getStrIngredient7(),
                mealDatabaseEntity.getStrIngredient8(),
                mealDatabaseEntity.getStrIngredient9(),
                mealDatabaseEntity.getStrIngredient10(),
                mealDatabaseEntity.getStrIngredient11(),
                mealDatabaseEntity.getStrIngredient12(),
                mealDatabaseEntity.getStrIngredient13(),
                mealDatabaseEntity.getStrIngredient14(),
                mealDatabaseEntity.getStrIngredient15(),
                mealDatabaseEntity.getStrIngredient16(),
                mealDatabaseEntity.getStrIngredient17(),
                mealDatabaseEntity.getStrIngredient18(),
                mealDatabaseEntity.getStrIngredient19(),
                mealDatabaseEntity.getStrIngredient20(),
                mealDatabaseEntity.getStrMeasure1(),
                mealDatabaseEntity.getStrMeasure2(),
                mealDatabaseEntity.getStrMeasure3(),
                mealDatabaseEntity.getStrMeasure4(),
                mealDatabaseEntity.getStrMeasure5(),
                mealDatabaseEntity.getStrMeasure6(),
                mealDatabaseEntity.getStrMeasure7(),
                mealDatabaseEntity.getStrMeasure8(),
                mealDatabaseEntity.getStrMeasure9(),
                mealDatabaseEntity.getStrMeasure10(),
                mealDatabaseEntity.getStrMeasure11(),
                mealDatabaseEntity.getStrMeasure12(),
                mealDatabaseEntity.getStrMeasure13(),
                mealDatabaseEntity.getStrMeasure14(),
                mealDatabaseEntity.getStrMeasure15(),
                mealDatabaseEntity.getStrMeasure16(),
                mealDatabaseEntity.getStrMeasure17(),
                mealDatabaseEntity.getStrMeasure18(),
                mealDatabaseEntity.getStrMeasure19(),
                mealDatabaseEntity.getStrMeasure20(),
                mealDatabaseEntity.getStrSource(),
                mealDatabaseEntity.getDateModified(),
                mealDatabaseEntity.isFavorite());
    }

    public MealsEntity transform(MealsApiEntity mealsApiEntity) {
        return new MealsEntity(mealsApiEntity.getMeals());
    }
}
