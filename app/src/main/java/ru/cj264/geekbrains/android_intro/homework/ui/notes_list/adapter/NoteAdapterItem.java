package ru.cj264.geekbrains.android_intro.homework.ui.notes_list.adapter;

import java.util.Objects;

import ru.cj264.geekbrains.android_intro.homework.domain.Note;

public class NoteAdapterItem implements AdapterItem {

    private final Note note;

    public NoteAdapterItem(Note note) {
        this.note = note;
    }

    public Note getNote() {
        return note;
    }

    @Override
    public String uniqueTag() {
        return "NoteAdapterItem_" + note.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteAdapterItem that = (NoteAdapterItem) o;
        return Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(note);
    }
}

