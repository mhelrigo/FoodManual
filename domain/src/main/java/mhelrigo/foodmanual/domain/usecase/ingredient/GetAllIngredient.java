package mhelrigo.foodmanual.domain.usecase.ingredient;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.entity.ingredient.IngredientsEntity;
import mhelrigo.foodmanual.domain.repository.IngredientRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class GetAllIngredient extends UseCase<Single<IngredientsEntity>, Void> {
    private IngredientRepository ingredientRepository;

    @Inject
    public GetAllIngredient(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Single<IngredientsEntity> execute(Void parameter) {
        return ingredientRepository.getAll();
    }
}
