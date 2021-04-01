package ru.cj264.geekbrains.android_intro.homework.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new MockNotesRepository();

    @Override
    public List<Note> getNotes() {
        List<Note> data = new ArrayList<>();

        data.add(new Note("1", "First",  "First note content",  LocalDateTime.of(2021, 3, 22, 3, 49)));
        data.add(new Note("2", "Second", "Second note content", LocalDateTime.of(2021, 3, 23, 3, 50)));
        data.add(new Note("3", "Third",  "Third note content",  LocalDateTime.of(2021, 3, 24, 3, 51)));
        data.add(new Note("4", "Fourth", "Fourth note content", LocalDateTime.of(2021, 3, 25, 3, 52)));
        data.add(new Note("5", "Fifth",  "Fifth note content",  LocalDateTime.of(2021, 3, 26, 3, 53)));
        data.add(new Note("6", "First",  "First note content",  LocalDateTime.of(2021, 3, 22, 3, 49)));
        data.add(new Note("7", "Second", "Second note content", LocalDateTime.of(2021, 3, 23, 3, 50)));
        data.add(new Note("8", "Third",  "Third note content",  LocalDateTime.of(2021, 3, 24, 3, 51)));
        data.add(new Note("9", "Fourth", "Fourth note content", LocalDateTime.of(2021, 3, 25, 3, 52)));
        data.add(new Note("10", "Fifth",  "Fifth note content",  LocalDateTime.of(2021, 3, 26, 3, 53)));
        data.add(new Note("11", "First",  "First note content",  LocalDateTime.of(2021, 3, 22, 3, 49)));
        data.add(new Note("12", "Second", "Second note content", LocalDateTime.of(2021, 3, 23, 3, 50)));
        data.add(new Note("13", "Third",  "Third note content",  LocalDateTime.of(2021, 3, 24, 3, 51)));
        data.add(new Note("14", "Fourth", "Fourth note content", LocalDateTime.of(2021, 3, 25, 3, 52)));
        data.add(new Note("15", "Fifth",  "Fifth note content",  LocalDateTime.of(2021, 3, 26, 3, 53)));
        data.add(new Note("16", "First",  "First note content",  LocalDateTime.of(2021, 3, 22, 3, 49)));
        data.add(new Note("17", "Second", "Second note content", LocalDateTime.of(2021, 3, 23, 3, 50)));
        data.add(new Note("18", "Third",  "Third note content",  LocalDateTime.of(2021, 3, 24, 3, 51)));
        data.add(new Note("19", "Fourth", "Fourth note content", LocalDateTime.of(2021, 3, 25, 3, 52)));
        data.add(new Note("20", "Fifth",  "Fifth note content",  LocalDateTime.of(2021, 3, 26, 3, 53)));

        return data;
    }
}
