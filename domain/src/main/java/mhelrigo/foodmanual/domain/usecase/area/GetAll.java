package mhelrigo.foodmanual.domain.usecase.area;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.area.Areas;
import mhelrigo.foodmanual.domain.repository.AreaRepository;
import mhelrigo.foodmanual.domain.usecase.base.UseCase;

public class GetAll extends UseCase<Single<Areas>, Void> {
    private AreaRepository areaRepository;

    public GetAll(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Override
    public Single<Areas> execute(Void parameter) {
        return areaRepository.getAll();
    }
}
