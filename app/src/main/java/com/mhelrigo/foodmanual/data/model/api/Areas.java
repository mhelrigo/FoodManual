package com.mhelrigo.foodmanual.data.model.api;

import com.google.gson.annotations.SerializedName;
import com.mhelrigo.foodmanual.data.model.Area;

import java.util.List;

public class Areas {
    @SerializedName("meals")
    private List<Area> area;

    public Areas(List<Area> area) {
        this.area = area;
    }

    public List<Area> getArea() {
        return area;
    }

    public void setArea(List<Area> area) {
        this.area = area;
    }
}
