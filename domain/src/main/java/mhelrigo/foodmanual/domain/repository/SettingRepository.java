package mhelrigo.foodmanual.domain.repository;

import io.reactivex.Completable;
import io.reactivex.Single;
import mhelrigo.foodmanual.domain.entity.settings.SettingEntity;

public interface SettingRepository {
    Single<SettingEntity> getSettings();
    Completable modifySettings(SettingEntity p0);
}
