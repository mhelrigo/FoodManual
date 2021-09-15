package mhelrigo.foodmanual.domain.model.area;

import java.util.List;

public class Areas {
    private List<Area> meals;

    public Areas(List<Area> meals) {
        this.meals = meals;
    }

    public List<Area> getMeals() {
        return meals;
    }

    public void setMeals(List<Area> meals) {
        this.meals = meals;
    }
}
