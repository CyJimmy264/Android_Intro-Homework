package ru.cj264.geekbrains.android_intro.homework.ui.notes_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.cj264.geekbrains.android_intro.homework.domain.Callback;
import ru.cj264.geekbrains.android_intro.homework.domain.Note;
import ru.cj264.geekbrains.android_intro.homework.domain.NotesRepository;

public class NotesListViewModel extends ViewModel {
    private final NotesRepository repository;

    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Note> newNoteLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> progressLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> deleteAtPositionData = new MutableLiveData<>();

    public NotesListViewModel(NotesRepository notesRepository) {
        repository = notesRepository;
    }

    public void fetchNotes() {
        progressLiveData.setValue(true);
        repository.getNotes(value -> {
            notesLiveData.setValue(value);
            progressLiveData.setValue(false);
        });
    }

    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }
    public LiveData<Note> getNewNoteLiveData() { return newNoteLiveData; }
    public LiveData<Boolean> getProgressLiveData() {
        return progressLiveData;
    }
    public LiveData<Integer> getDeleteAtPositionData() { return deleteAtPositionData; }

    public void addNewNote() {
        progressLiveData.setValue(true);
        repository.addNewNote(value -> {
            newNoteLiveData.postValue(value);
            progressLiveData.setValue(false);
        });
    }

    public void clearNotes() {
        progressLiveData.setValue(true);
        repository.clearNotes(voidValue -> {
            notesLiveData.postValue(new ArrayList<>());
            progressLiveData.setValue(false);
        });
    }

    public void deleteAtPosition(int contextMenuItemPosition) {
        deleteAtPositionData.setValue(contextMenuItemPosition);
    }

}
