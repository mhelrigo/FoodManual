package mhelrigo.foodmanual.domain;

import mhelrigo.foodmanual.domain.model.category.Categories;
import mhelrigo.foodmanual.domain.model.category.Category;
import mhelrigo.foodmanual.domain.model.meal.Meal;

public class CategoriesTestModel {
    public static Categories categories;

    public CategoriesTestModel() {
    }

    public void addCategory(Category category) {
        categories.getCategories().add(category);
    }

    public static Category mockCategory(String id) {
        return new Category(id,
                "Beef",
                "https://www.themealdb.com/images/category/beef.png",
                "Beef is the culinary name for meat from cattle, particularly skeletal muscle. Humans have been eating beef since prehistoric times.[1] Beef is a source of high-quality protein and essential nutrients.[2]");
    }
}
