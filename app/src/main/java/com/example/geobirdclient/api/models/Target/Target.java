package com.example.geobirdclient.api.models.Target;


public class Target {
    private String code;
    private String name;
    private String description;
    private Integer points;
    private String image;
    private String locX;
    private String locY;

    public Target(String code, String name, String description, Integer points, String image, String locX, String locY) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.points = points;
        this.image = image;
        this.locX = locX;
        this.locY = locY;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocX() {
        return locX;
    }

    public void setLocX(String locX) {
        this.locX = locX;
    }

    public String getLocY() {
        return locY;
    }

    public void setLocY(String locY) {
        this.locY = locY;
    }

    @Override
    public String toString() {
        return "Target{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", points=" + points +
                ", image='" + image + '\'' +
                ", locX='" + locX + '\'' +
                ", locY='" + locY + '\'' +
                '}';
    }
}
