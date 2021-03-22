package ru.cj264.geekbrains.android_intro.homework.domain;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

public class MockNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new MockNotesRepository();

    @Override
    public List<Note> getNotes() {
        ArrayList<Note> data = new ArrayList<>();

        data.add(new Note("1", "First",  "First note content",  LocalDateTime.of(2021, 3, 23, 3, 49)));
        data.add(new Note("2", "Second", "Second note content", LocalDateTime.of(2021, 3, 23, 3, 50)));
        data.add(new Note("3", "Third",  "Third note content",  LocalDateTime.of(2021, 3, 23, 3, 51)));
        data.add(new Note("4", "Fourth", "Fourth note content", LocalDateTime.of(2021, 3, 23, 3, 52)));
        data.add(new Note("5", "Fifth",  "Fifth note content",  LocalDateTime.of(2021, 3, 23, 3, 53)));

        return null;
    }
}
