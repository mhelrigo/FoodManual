package mhelrigo.foodmanual.data.repository.meal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import mhelrigo.foodmanual.data.repository.meal.local.MealDao;
import mhelrigo.foodmanual.data.mapper.MealMapper;
import mhelrigo.foodmanual.data.repository.meal.remote.MealApi;
import mhelrigo.foodmanual.domain.model.meal.Meal;
import mhelrigo.foodmanual.domain.model.meal.Meals;
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
    public Single<Meals> getLatest() {
        return mealApi.getLatest();
    }

    @Override
    public Single<Meals> getRandomly() {
        return mealApi.getRandomly();
    }

    @Override
    public Single<Meals> getDetails(String id) {
        return mealApi.getDetails(id);
    }

    @Override
    public Single<Meals> searchByCategory(String category) {
        return mealApi.searchByCategory(category);
    }

    @Override
    public Completable addFavorite(Meal meal) {
        return mealDao.addFavorite(mealMapper.toDatabase(meal));
    }

    @Override
    public Completable removeFavorite(Meal meal) {
        return mealDao.removeFavorite(mealMapper.toDatabase(meal));
    }

    @Override
    public Single<List<Meal>> getAllFavorites() {
        return mealDao.getAllFavorites()
                .flatMapObservable(Observable::fromIterable)
                .map(mealMapper::toBusinessData)
                .toList();
    }
}
