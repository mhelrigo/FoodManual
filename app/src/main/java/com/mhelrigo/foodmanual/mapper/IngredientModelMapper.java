package com.mhelrigo.foodmanual.mapper;

import com.mhelrigo.foodmanual.model.ingredient.IngredientModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import mhelrigo.foodmanual.domain.entity.ingredient.IngredientEntity;

@Singleton
public class IngredientModelMapper {
    @Inject
    public IngredientModelMapper() {
    }

    public IngredientModel transform(IngredientEntity ingredientEntity) {
        return new IngredientModel(ingredientEntity.getIdIngredient(),
                ingredientEntity.getStrIngredient(),
                ingredientEntity.getStrDescription(),
                ingredientEntity.getStrType());
    }
}
