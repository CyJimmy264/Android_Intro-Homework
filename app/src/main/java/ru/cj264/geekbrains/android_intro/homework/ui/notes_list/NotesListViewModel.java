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

    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> progressLiveData = new MutableLiveData<>();

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

    public LiveData<List<AdapterItem>> getNotesLiveData() {
        return Transformations.map(notesLiveData, input -> {
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
            List<Note> currentNotes = notesLiveData.getValue();
            currentNotes.add(value);
            notesLiveData.postValue(currentNotes);
            progressLiveData.setValue(false);
        });
    }

    public void clearNotes() {
        progressLiveData.setValue(true);
        repository.clearNotes(voidValue -> {
            // TODO: clear repository? Danger!
            notesLiveData.postValue(new ArrayList<>());
            progressLiveData.setValue(false);
        });
    }

    public void deleteAtPosition(int contextMenuItemPosition) {
        progressLiveData.setValue(true);
        // TODO: repository delete
        repository.addNewNote(value -> {
            ArrayList<Note> currentNotes = new ArrayList<>(notesLiveData.getValue());
            currentNotes.remove(contextMenuItemPosition);
            notesLiveData.postValue(currentNotes);
            progressLiveData.setValue(false);
        });
    }

}
