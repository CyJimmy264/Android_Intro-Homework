package ru.cj264.geekbrains.android_intro.homework.ui.notes_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.cj264.geekbrains.android_intro.homework.domain.FirestoreNotesRepository

class NotesListViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotesListViewModel(FirestoreNotesRepository.INSTANCE) as T
    }
}