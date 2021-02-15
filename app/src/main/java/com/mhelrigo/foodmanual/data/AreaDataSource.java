package com.mhelrigo.foodmanual.data;

import com.mhelrigo.foodmanual.data.model.api.Areas;

import io.reactivex.Flowable;

public interface AreaDataSource {
    Flowable<Areas> fetchAreas();
}
