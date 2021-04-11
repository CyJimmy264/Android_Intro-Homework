package ru.cj264.geekbrains.android_intro.homework.domain;


import java.time.LocalDateTime;
import java.util.Objects;

public class Note {
    private final String id;
    private final LocalDateTime creationDateTime;
    private String title;
    private String description;
    private String imageUrl;

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

    public String getImageUrl() { return imageUrl; }

    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return getId().equals(note.getId()) &&
                getCreationDateTime().equals(note.getCreationDateTime()) &&
                Objects.equals(getTitle(), note.getTitle()) &&
                Objects.equals(getDescription(), note.getDescription()) &&
                Objects.equals(getImageUrl(), note.getImageUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreationDateTime(), getTitle(), getDescription(), getImageUrl());
    }
}
