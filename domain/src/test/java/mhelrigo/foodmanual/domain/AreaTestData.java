package mhelrigo.foodmanual.domain;

import java.util.ArrayList;

import mhelrigo.foodmanual.domain.model.area.Area;
import mhelrigo.foodmanual.domain.model.area.Areas;

public class AreaTestData {
    public Areas areas;

    public AreaTestData() {
        areas = new Areas(new ArrayList<>());
    }

    public void addArea(Area area) {
        areas.getMeals().add(area);
    }

    public static Area mockArea(String area) {
        return new Area(area);
    }
}
