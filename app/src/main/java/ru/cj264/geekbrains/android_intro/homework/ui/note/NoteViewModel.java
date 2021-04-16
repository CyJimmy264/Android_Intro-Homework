package ru.cj264.geekbrains.android_intro.homework.ui.note;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.cj264.geekbrains.android_intro.homework.domain.FirestoreNotesRepository;
import ru.cj264.geekbrains.android_intro.homework.domain.Note;
import ru.cj264.geekbrains.android_intro.homework.domain.NotesRepository;

public class NoteViewModel extends ViewModel {
    private final NotesRepository repository;

    private final MutableLiveData<Note> noteLiveData = new MutableLiveData<>();
    private final String noteId;

    public NoteViewModel(String noteId, NotesRepository repository) {
        this.noteId = noteId;
        this.repository = repository;

        repository.getNote(noteId, noteLiveData::setValue);
    }

    public LiveData<Note> getNote() {
        return noteLiveData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final String noteId;

        private final NotesRepository repository;

        public Factory(String noteId) {
            this.noteId = noteId;
            repository = FirestoreNotesRepository.INSTANCE;
        }

        @Override
        @NonNull
        @SuppressWarnings("unchecked")
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new NoteViewModel(noteId, repository);
        }
    }

}
