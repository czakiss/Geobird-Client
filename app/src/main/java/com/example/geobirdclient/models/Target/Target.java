package com.example.geobirdclient.models.Target;


public class Target {
    private Integer id;
    private String name;
    private String description;
    private Integer points;
    private String image;
    private String code;
    private String locX;
    private String locY;

    public Target(Integer id, String name, String description, Integer points, String image, String code, String locX, String locY) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.points = points;
        this.image = image;
        this.code = code;
        this.locX = locX;
        this.locY = locY;
    }

    public Target() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
