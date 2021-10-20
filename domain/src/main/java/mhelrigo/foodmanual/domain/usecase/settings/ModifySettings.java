package mhelrigo.foodmanual.domain.usecase.settings;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import mhelrigo.foodmanual.domain.entity.settings.SettingEntity;
import mhelrigo.foodmanual.domain.repository.SettingRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

@Singleton
public class ModifySettings extends UseCase<Completable, ModifySettings.Params> {
    private SettingRepository settingRepository;

    @Inject
    public ModifySettings(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public Completable execute(Params parameter) {
        return settingRepository.modifySettings(parameter.settingEntity);
    }

    public static final class Params {
        public SettingEntity settingEntity;

        private Params(SettingEntity settingEntity) {
            this.settingEntity = settingEntity;
        }

        public static final Params params(SettingEntity settingEntity) {
            return new Params(settingEntity);
        }
    }
}
