package ru.cj264.geekbrains.android_intro.homework.domain;


import java.time.LocalDateTime;

public class Note {
    private final String id;
    private final LocalDateTime creationDateTime;
    private final String title;
    private final String description;
    private String imageUrl = "https://img3.akspic.ru/originals/2/8/8/7/4/147882-poni-rozovyj-liniya-kartinka-televideniye-750x1334.jpg";

    public Note(String id, String title, String description, LocalDateTime creationDateTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDateTime = creationDateTime;
    }

    public String getId() { return id; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public LocalDateTime getCreationDateTime() { return creationDateTime; }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
