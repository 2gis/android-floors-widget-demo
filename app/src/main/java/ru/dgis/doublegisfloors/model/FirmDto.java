package ru.dgis.doublegisfloors.model;

import com.google.gson.annotations.SerializedName;

public class FirmDto {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("floor")
    private String floor;
    @SerializedName("main_rubric")
    private String mainRubric;

    public FirmDto(long id, String name, String floor, String mainRubric) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.mainRubric = mainRubric;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFloor() {
        return floor;
    }

    public String getMainRubric() {
        return mainRubric;
    }
}
