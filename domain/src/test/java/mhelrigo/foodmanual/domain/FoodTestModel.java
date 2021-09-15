package mhelrigo.foodmanual.domain;

import java.util.ArrayList;

import mhelrigo.foodmanual.domain.model.meal.Meal;
import mhelrigo.foodmanual.domain.model.meal.Meals;

public class FoodTestModel {
    public static final String FAKE_MEAL_ID = "52771";

    public Meals meals;

    public FoodTestModel() {
        meals = new Meals(new ArrayList<>());
    }

    public void addMeal(Meal meal) {
        meals.getMeals().add(meal);
    }

    public static Meal mockMeal(String id) {
        return new Meal(id,
                "Spicy Arrabiata Penne",
                null,
                "Vegetarian",
                "Italian",
                "Bring a large pot of water to a boil. Add kosher salt to the boiling water, then add the pasta. Cook according to the package instructions, about 9 minutes.\r\nIn a large skillet over medium-high heat, add the olive oil and heat until the oil starts to shimmer. Add the garlic and cook, stirring, until fragrant, 1 to 2 minutes. Add the chopped tomatoes, red chile flakes, Italian seasoning and salt and pepper to taste. Bring to a boil and cook for 5 minutes. Remove from the heat and add the chopped basil.\r\nDrain the pasta and add it to the sauce. Garnish with Parmigiano-Reggiano flakes and more basil and serve warm.",
                "https://www.themealdb.com/images/media/meals/ustsqw1468250014.jpg",
                "Pasta,Curry",
                "https://www.youtube.com/watch?v=1IszT_guI08",
                "penne rigate",
                "olive oil",
                "garlic",
                "chopped tomatoes",
                "red chile flakes",
                "italian seasoning",
                "basil",
                "Parmigiano-Reggiano",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                null,
                null,
                null,
                null,
                null,
                "1 pound",
                "1/4 cup",
                "3 cloves",
                "1 tin ",
                "1/2 teaspoon",
                "1/2 teaspoon",
                "6 leaves",
                "spinkling",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false);
    }

    public Meal mockMeal(String id, boolean isFavorite) {
        return new Meal(id,
                "Spicy Arrabiata Penne" + id,
                null,
                "Vegetarian",
                "Italian",
                "Bring a large pot of water to a boil. Add kosher salt to the boiling water, then add the pasta. Cook according to the package instructions, about 9 minutes.\r\nIn a large skillet over medium-high heat, add the olive oil and heat until the oil starts to shimmer. Add the garlic and cook, stirring, until fragrant, 1 to 2 minutes. Add the chopped tomatoes, red chile flakes, Italian seasoning and salt and pepper to taste. Bring to a boil and cook for 5 minutes. Remove from the heat and add the chopped basil.\r\nDrain the pasta and add it to the sauce. Garnish with Parmigiano-Reggiano flakes and more basil and serve warm.",
                "https://www.themealdb.com/images/media/meals/ustsqw1468250014.jpg",
                "Pasta,Curry",
                "https://www.youtube.com/watch?v=1IszT_guI08",
                "penne rigate",
                "olive oil",
                "garlic",
                "chopped tomatoes",
                "red chile flakes",
                "italian seasoning",
                "basil",
                "Parmigiano-Reggiano",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                null,
                null,
                null,
                null,
                null,
                "1 pound",
                "1/4 cup",
                "3 cloves",
                "1 tin ",
                "1/2 teaspoon",
                "1/2 teaspoon",
                "6 leaves",
                "spinkling",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                isFavorite);
    }
}






















