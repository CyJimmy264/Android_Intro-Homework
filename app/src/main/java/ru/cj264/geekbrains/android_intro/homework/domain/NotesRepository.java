package ru.cj264.geekbrains.android_intro.homework.domain;

import java.util.List;

public interface NotesRepository {
    void getNotes(Callback<List<Note>> callback);
}
