package com.shorturl.shorturldemo.dto;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class ShortUrlDTO implements Serializable {

    private String id;
    private String originalURL;
    private String createdOn;
    private String shortURL;

    public ShortUrlDTO(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    @Override
    public String toString() {
        return "ShortUrlDTO{" +
                "id='" + id + '\'' +
                ", originalURL='" + originalURL + '\'' +
                ", createdOn='" + createdOn + '\'' +
                ", shortURL='" + shortURL + '\'' +
                '}';
    }
}
