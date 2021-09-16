package mhelrigo.foodmanual.data.repository;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Single;
import mhelrigo.foodmanual.data.repository.area.AreaRepositoryImpl;
import mhelrigo.foodmanual.data.repository.area.remote.AreaApi;
import mhelrigo.foodmanual.domain.model.area.Areas;
import mhelrigo.foodmanual.domain.repository.AreaRepository;

@RunWith(MockitoJUnitRunner.class)
public class AreaRepositoryTest {
    private AreaRepository areaRepository;

    @Mock
    AreaApi areaApi;

    @Mock
    Areas areas;

    @Before
    public void setUp() throws Exception {
        areaRepository = new AreaRepositoryImpl(areaApi);
    }

    @Test
    public void getAll() {
        given(areaApi.getAll()).willReturn(Single.just(areas));
        areaRepository.getAll().test();

        verify(areaApi).getAll();
        verifyNoMoreInteractions(areaApi);
    }
}
