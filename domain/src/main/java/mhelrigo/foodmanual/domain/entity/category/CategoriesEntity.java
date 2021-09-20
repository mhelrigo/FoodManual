package mhelrigo.foodmanual.domain.entity.category;

import java.util.List;

public class CategoriesEntity {
    private List<CategoryEntity> categories;

    public CategoriesEntity(List<CategoryEntity> categories) {
        this.categories = categories;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }
}
