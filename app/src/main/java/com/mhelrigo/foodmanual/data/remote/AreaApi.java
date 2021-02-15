package com.mhelrigo.foodmanual.data.remote;

import com.mhelrigo.foodmanual.data.model.api.Areas;
import com.mhelrigo.foodmanual.utils.Constants;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface AreaApi {
    @GET(Constants.AREAS_URL)
    Flowable<Areas> fetchAreas();
}
