package mhelrigo.foodmanual.data.repository.area.remote;

import io.reactivex.Single;
import mhelrigo.foodmanual.domain.model.area.Areas;
import retrofit2.http.GET;

public interface AreaApi {
    @GET("list.php?a=list")
    Single<Areas> getAll();
}
