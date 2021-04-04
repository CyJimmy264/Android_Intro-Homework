package ru.cj264.geekbrains.android_intro.homework.ui.notes_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.cj264.geekbrains.android_intro.homework.domain.Note;
import ru.cj264.geekbrains.android_intro.homework.domain.NotesRepository;

public class NotesListViewModel extends ViewModel {
    private final NotesRepository repository;

    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();

    public NotesListViewModel(NotesRepository notesRepository) {
        repository = notesRepository;
    }

    public void fetchNotes() { repository.getNotes(notesLiveData::setValue); }

    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }
}
