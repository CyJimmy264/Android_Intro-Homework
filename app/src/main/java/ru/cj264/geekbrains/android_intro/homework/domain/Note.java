package ru.cj264.geekbrains.android_intro.homework.domain;


import java.time.LocalDateTime;

public class Note {
    private final String id;
    private final String title;
    private final String description;
    private final LocalDateTime creationDateTime;

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
}
