package mhelrigo.foodmanual.data.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Single;
import mhelrigo.foodmanual.data.repository.category.CategoryRepositoryImpl;
import mhelrigo.foodmanual.data.repository.category.remote.CategoryApi;
import mhelrigo.foodmanual.domain.model.category.Categories;
import mhelrigo.foodmanual.domain.repository.CategoryRepository;

@RunWith(MockitoJUnitRunner.class)
public class CategoryRepositoryTest {
    private CategoryRepository categoryRepository;

    @Mock
    CategoryApi categoryApi;

    @Mock
    Categories categories;

    @Before
    public void setUp() throws Exception {
        categoryRepository = new CategoryRepositoryImpl(categoryApi);
    }

    @Test
    public void getAll() {
        given(categoryApi.getAll()).willReturn(Single.just(categories));
        categoryRepository.getAll().test();

        verify(categoryApi).getAll();
        verifyNoMoreInteractions(categoryApi);
    }
}
