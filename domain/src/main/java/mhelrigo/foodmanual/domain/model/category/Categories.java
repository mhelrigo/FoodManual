package mhelrigo.foodmanual.domain.model.category;

import java.util.List;

public class Categories {
    private List<Category> meals;

    public Categories(List<Category> meals) {
        this.meals = meals;
    }

    public List<Category> getMeals() {
        return meals;
    }

    public void setMeals(List<Category> meals) {
        this.meals = meals;
    }
}
