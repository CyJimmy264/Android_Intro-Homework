package ru.cj264.geekbrains.android_intro.homework.ui.notes_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.cj264.geekbrains.android_intro.homework.domain.MockNotesRepository;
import ru.cj264.geekbrains.android_intro.homework.domain.Note;
import ru.cj264.geekbrains.android_intro.homework.domain.NotesRepository;

public class NotesListViewModel extends ViewModel {
    private final NotesRepository repository = MockNotesRepository.INSTANCE;

    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();

    public void fetchNotes() {
        notesLiveData.setValue(repository.getNotes());
    }

    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }
}
