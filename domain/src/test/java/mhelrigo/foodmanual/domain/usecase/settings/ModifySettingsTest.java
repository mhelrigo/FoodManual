package mhelrigo.foodmanual.domain.usecase.settings;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mhelrigo.foodmanual.domain.entity.settings.SettingEntity;
import mhelrigo.foodmanual.domain.repository.SettingRepository;

@RunWith(MockitoJUnitRunner.class)
public class ModifySettingsTest {
    private ModifySettings modifySettings;

    @Mock
    private SettingRepository settingRepository;

    @Mock
    private SettingEntity settingEntity;

    @Before
    public void setUp() throws Exception {
        modifySettings = new ModifySettings(settingRepository);
    }

    @Test
    public void modifySettings() {
        modifySettings.execute(ModifySettings.Params.params(settingEntity));
        verify(settingRepository).modifySettings(settingEntity);
        verifyNoMoreInteractions(settingEntity);
    }
}
