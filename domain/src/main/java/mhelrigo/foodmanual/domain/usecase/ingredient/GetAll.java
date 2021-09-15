package mhelrigo.foodmanual.domain.usecase.ingredient;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.ingredient.Ingredients;
import mhelrigo.foodmanual.domain.repository.IngredientRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

public class GetAll extends UseCase<Single<Ingredients>, Void> {
    private IngredientRepository ingredientRepository;

    public GetAll(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Single<Ingredients> execute(Void parameter) {
        return ingredientRepository.getAll();
    }
}
