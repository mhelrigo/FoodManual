package mhelrigo.foodmanual.domain.repository;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.area.Areas;

public interface AreaRepository {
    Single<Areas> getAll();
}
