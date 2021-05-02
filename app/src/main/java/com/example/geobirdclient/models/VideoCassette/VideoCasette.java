package com.example.geobirdclient.models.VideoCassette;

import java.time.LocalDate;

public class VideoCasette {
    private Long id;
    private String title;
    private LocalDate productionYear;

    public VideoCasette(Long id, String title, LocalDate productionYear) {
        this.id = id;
        this.title = title;
        this.productionYear = productionYear;
    }

    public VideoCasette() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(LocalDate productionYear) {
        this.productionYear = productionYear;
    }
}
