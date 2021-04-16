package ru.cj264.geekbrains.android_intro.homework.ui.notes_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.cj264.geekbrains.android_intro.homework.domain.Note;
import ru.cj264.geekbrains.android_intro.homework.domain.NotesRepository;
import ru.cj264.geekbrains.android_intro.homework.ui.notes_list.adapter.AdapterItem;
import ru.cj264.geekbrains.android_intro.homework.ui.notes_list.adapter.NoteAdapterItem;

public class NotesListViewModel extends ViewModel {
    private final NotesRepository repository;

    private final MutableLiveData<ArrayList<Note>> notesListLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> progressLiveData = new MutableLiveData<>();

    public NotesListViewModel(NotesRepository notesRepository) {
        repository = notesRepository;
    }

    public void fetchNotes() {
        progressLiveData.setValue(true);
        repository.getNotes(value -> {
            notesListLiveData.setValue(value);
            progressLiveData.setValue(false);
        });
    }

    public LiveData<List<AdapterItem>> getNotesListLiveData() {
        return Transformations.map(notesListLiveData, input -> {
            ArrayList<AdapterItem> output = new ArrayList<>();

            Collections.sort(input, (o1, o2)
                    -> o1.getCreationDateTime().compareTo(o2.getCreationDateTime()));

            for (Note note : input) {
                output.add(new NoteAdapterItem(note));
            }

            return output;
        });
    }

    public LiveData<Boolean> getProgressLiveData() {
        return progressLiveData;
    }

    public void addNewNote() {
        progressLiveData.setValue(true);
        repository.addNewNote(value -> {
            ArrayList<Note> currentNotes = notesListLiveData.getValue();
            currentNotes.add(value);
            notesListLiveData.postValue(currentNotes);
            progressLiveData.setValue(false);
        });
    }

    public void clearNotes() {
        progressLiveData.setValue(true);
        repository.clearNotes(voidValue -> {
            // TODO: clear repository? Danger!
            notesListLiveData.postValue(new ArrayList<>());
            progressLiveData.setValue(false);
        });
    }

    public void deleteAtPosition(int contextMenuItemPosition) {
        progressLiveData.setValue(true);
        // TODO: repository delete
        repository.addNewNote(value -> {
            ArrayList<Note> currentNotes = new ArrayList<>(notesListLiveData.getValue());
            currentNotes.remove(contextMenuItemPosition);
            notesListLiveData.postValue(currentNotes);
            progressLiveData.setValue(false);
        });
    }

}
