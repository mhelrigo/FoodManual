package mhelrigo.foodmanual.domain.usecase.area;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import mhelrigo.foodmanual.domain.repository.AreaRepository;

@RunWith(MockitoJUnitRunner.class)
public class GetAllTest {
    private GetAll getAll;

    @Mock
    public AreaRepository areaRepository;

    @Before
    public void setUp() throws Exception {
        getAll = new GetAll(areaRepository);
    }

    @Test
    public void getAllSuccess() {
        getAll.execute(null);
        verify(areaRepository).getAll();
        verifyNoMoreInteractions(areaRepository);
    }
}
