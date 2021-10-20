package mhelrigo.foodmanual.domain.usecase.settings;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.entity.settings.SettingEntity;
import mhelrigo.foodmanual.domain.repository.SettingRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class GetSettings extends UseCase<Single<SettingEntity>, Void> {
    private SettingRepository settingRepository;

    @Inject
    public GetSettings(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public Single<SettingEntity> execute(Void parameter) {
        return settingRepository.getSettings();
    }
}
