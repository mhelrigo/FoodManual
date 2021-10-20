package mhelrigo.foodmanual.data.repository.settings;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;
import mhelrigo.foodmanual.domain.entity.settings.SettingEntity;
import mhelrigo.foodmanual.domain.repository.SettingRepository;

@Singleton
public class SettingRepositoryImpl implements SettingRepository {
    public static final String SETTINGS_NIGHT_MODE = "SETTINGS_NIGHT_MODE";

    private SharedPreferences sharedPreferences;

    private Boolean defaultNightMode = true;

    @Inject
    public SettingRepositoryImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Single<SettingEntity> getSettings() {
        SettingEntity settingEntity = new SettingEntity();
        settingEntity.setNightMode(sharedPreferences.getBoolean(SETTINGS_NIGHT_MODE, defaultNightMode));
        return Single.just(settingEntity);
    }

    @Override
    public Completable modifySettings(SettingEntity p0) {
        sharedPreferences.edit().putBoolean(SETTINGS_NIGHT_MODE, p0.getNightMode()).apply();

        return Completable.complete();
    }
}
