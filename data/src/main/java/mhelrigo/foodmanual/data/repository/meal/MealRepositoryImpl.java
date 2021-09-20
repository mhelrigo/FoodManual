package mhelrigo.foodmanual.data.repository.meal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import mhelrigo.foodmanual.data.mapper.MealDatabaseMapper;
import mhelrigo.foodmanual.data.repository.meal.local.MealDao;
import mhelrigo.foodmanual.data.repository.meal.remote.MealApi;
import mhelrigo.foodmanual.domain.entity.meal.MealEntity;
import mhelrigo.foodmanual.domain.entity.meal.MealsEntity;
import mhelrigo.foodmanual.domain.repository.MealRepository;

@Singleton
public class MealRepositoryImpl implements MealRepository {
    private MealApi mealApi;
    private MealDao mealDao;
    private MealDatabaseMapper mealDatabaseMapper;

    @Inject
    public MealRepositoryImpl(MealApi mealApi, MealDao mealDao, MealDatabaseMapper mealDatabaseMapper) {
        this.mealApi = mealApi;
        this.mealDao = mealDao;
        this.mealDatabaseMapper = mealDatabaseMapper;
    }

    @Override
    public Single<MealsEntity> getLatest() {
        return mealApi.getLatest();
    }

    @Override
    public Single<MealsEntity> getRandomly() {
        return mealApi.getRandomly();
    }

    @Override
    public Single<MealsEntity> getDetails(String id) {
        return mealApi.getDetails(id);
    }

    @Override
    public Single<MealsEntity> searchByCategory(String category) {
        return mealApi.searchByCategory(category);
    }

    @Override
    public Completable addFavorite(MealEntity mealEntity) {
        return mealDao.addFavorite(mealDatabaseMapper.transform(mealEntity));
    }

    @Override
    public Completable removeFavorite(MealEntity mealEntity) {
        return mealDao.removeFavorite(mealDatabaseMapper.transform(mealEntity));
    }

    @Override
    public Single<List<MealEntity>> getAllFavorites() {
        return mealDao.getAllFavorites()
                .flatMapObservable(Observable::fromIterable)
                .map(mealDatabaseMapper::transform)
                .toList();
    }
}
