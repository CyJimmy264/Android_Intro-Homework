package ru.cj264.geekbrains.android_intro.homework.ui.notes_list;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.cj264.geekbrains.android_intro.homework.domain.FirestoreNotesRepository;
import ru.cj264.geekbrains.android_intro.homework.domain.MockNotesRepository;

public class NotesListViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NotesListViewModel(FirestoreNotesRepository.INSTANCE);
    }
}
