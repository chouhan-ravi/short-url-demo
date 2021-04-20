package com.shorturl.shorturldemo.model;

import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="short_urls")
@AllArgsConstructor
public class ShortURL extends AbstractModel {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "created_on", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "original_url")
    private String originalURL;

    @Column(name = "short_url")
    private String shortURL;

    public ShortURL() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }
}
