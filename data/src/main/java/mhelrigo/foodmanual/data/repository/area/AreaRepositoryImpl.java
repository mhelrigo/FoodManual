package mhelrigo.foodmanual.data.repository.area;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import mhelrigo.foodmanual.data.repository.area.remote.AreaApi;
import mhelrigo.foodmanual.domain.model.area.Areas;
import mhelrigo.foodmanual.domain.repository.AreaRepository;

@Singleton
public class AreaRepositoryImpl implements AreaRepository {
    private AreaApi areaApi;

    @Inject
    public AreaRepositoryImpl(AreaApi areaApi) {
        this.areaApi = areaApi;
    }

    @Override
    public Single<Areas> getAll() {
        return areaApi.getAll();
    }
}
