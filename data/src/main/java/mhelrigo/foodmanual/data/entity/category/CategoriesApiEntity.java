package mhelrigo.foodmanual.data.entity.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import mhelrigo.foodmanual.domain.entity.category.CategoryEntity;

public class CategoriesApiEntity {
    @SerializedName("categories")
    private List<CategoryEntity> categoryEntities;

    public CategoriesApiEntity(List<CategoryEntity> categoryEntities) {
        this.categoryEntities = categoryEntities;
    }

    public List<CategoryEntity> getCategoryEntities() {
        return categoryEntities;
    }

    public void setCategoryEntities(List<CategoryEntity> categoryEntities) {
        this.categoryEntities = categoryEntities;
    }
}
