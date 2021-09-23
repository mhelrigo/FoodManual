package mhelrigo.foodmanual.data.repository.meal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import mhelrigo.foodmanual.data.mapper.MealMapper;
import mhelrigo.foodmanual.data.repository.meal.local.MealDao;
import mhelrigo.foodmanual.data.repository.meal.remote.MealApi;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;
import mhelrigo.foodmanual.domain.entity.meal.MealsEntity;
import mhelrigo.foodmanual.domain.repository.MealRepository;

@Singleton
public class MealRepositoryImpl implements MealRepository {
    private MealApi mealApi;
    private MealDao mealDao;
    private MealMapper mealMapper;

    @Inject
    public MealRepositoryImpl(MealApi mealApi, MealDao mealDao, MealMapper mealMapper) {
        this.mealApi = mealApi;
        this.mealDao = mealDao;
        this.mealMapper = mealMapper;
    }

    @Override
    public Single<MealsEntity> getLatest() {
        return mealApi.getLatest()
                .map(mealsApiEntity -> mealMapper.transform(mealsApiEntity));
    }

    @Override
    public Single<MealsEntity> getRandomly() {
        return mealApi.getRandomly()
                .map(mealsApiEntity -> mealMapper.transform(mealsApiEntity));
    }

    @Override
    public Single<MealsEntity> getDetails(String id) {
        return mealApi.getDetails(id)
                .map(mealsApiEntity -> mealMapper.transform(mealsApiEntity));
    }

    @Override
    public Single<MealsEntity> searchByCategory(String category) {
        return mealApi.searchByCategory(category)
                .map(mealsApiEntity -> mealMapper.transform(mealsApiEntity));
    }

    @Override
    public Completable addFavorite(MealEntity mealEntity) {
        return mealDao.addFavorite(mealMapper.transform(mealEntity));
    }

    @Override
    public Completable removeFavorite(MealEntity mealEntity) {
        return mealDao.removeFavorite(mealMapper.transform(mealEntity));
    }

    @Override
    public Single<List<MealEntity>> getAllFavorites() {
        return mealDao.getAllFavorites()
                .flatMapObservable(Observable::fromIterable)
                .map(mealMapper::transform)
                .toList();
    }

    @Override
    public Single<MealsEntity> searchByName(String name) {
        return mealApi.searchByName(name).map(mealsApiEntity -> mealMapper.transform(mealsApiEntity));
    }

    @Override
    public Single<MealsEntity> filterByMainIngredient(String ingredient) {
        return mealApi.filterByMainIngredient(ingredient).map(mealsApiEntity -> mealMapper.transform(mealsApiEntity));
    }
}
