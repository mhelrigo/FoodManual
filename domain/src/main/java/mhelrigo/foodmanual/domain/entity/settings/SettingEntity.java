package mhelrigo.foodmanual.domain.entity.settings;

public class SettingEntity {
    private Boolean isNightMode;

    public SettingEntity() {
    }

    public SettingEntity(Boolean isNightMode) {
        this.isNightMode = isNightMode;
    }

    public Boolean getNightMode() {
        return isNightMode;
    }

    public void setNightMode(Boolean nightMode) {
        isNightMode = nightMode;
    }
}
