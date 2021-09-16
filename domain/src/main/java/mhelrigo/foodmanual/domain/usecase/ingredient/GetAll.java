package mhelrigo.foodmanual.domain.usecase.ingredient;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.ingredient.Ingredients;
import mhelrigo.foodmanual.domain.repository.IngredientRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class GetAll extends UseCase<Single<Ingredients>, Void> {
    private IngredientRepository ingredientRepository;

    @Inject
    public GetAll(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Single<Ingredients> execute(Void parameter) {
        return ingredientRepository.getAll();
    }
}
