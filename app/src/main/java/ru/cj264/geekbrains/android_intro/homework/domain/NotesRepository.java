package ru.cj264.geekbrains.android_intro.homework.domain;

import java.util.ArrayList;
import java.util.List;

public interface NotesRepository {
    void getNotes(Callback<ArrayList<Note>> callback);

    void getNote(String noteId, Callback<Note> callback);

    void clearNotes(Callback<Void> voidCallback);

    void addNewNote(Callback<Note> callback);
}
