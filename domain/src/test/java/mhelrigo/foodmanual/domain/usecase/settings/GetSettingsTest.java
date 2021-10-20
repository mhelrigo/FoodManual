package mhelrigo.foodmanual.domain.usecase.settings;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mhelrigo.foodmanual.domain.repository.SettingRepository;

@RunWith(MockitoJUnitRunner.class)
public class GetSettingsTest {
    private GetSettings getSettings;

    @Mock
    private SettingRepository settingRepository;

    @Before
    public void setUp() throws Exception {
        getSettings = new GetSettings(settingRepository);
    }

    @Test
    public void getSettings() {
        getSettings.execute(null);
        verify(settingRepository).getSettings();
        verifyNoMoreInteractions(settingRepository);
    }
}
