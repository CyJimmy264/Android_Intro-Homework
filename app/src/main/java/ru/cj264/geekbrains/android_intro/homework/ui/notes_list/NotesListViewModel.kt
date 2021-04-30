package ru.cj264.geekbrains.android_intro.homework.ui.notes_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.cj264.geekbrains.android_intro.homework.domain.Note
import ru.cj264.geekbrains.android_intro.homework.domain.NotesRepository
import ru.cj264.geekbrains.android_intro.homework.ui.notes_list.adapter.AdapterItem
import ru.cj264.geekbrains.android_intro.homework.ui.notes_list.adapter.NoteAdapterItem
import java.util.*

class NotesListViewModel(private val repository: NotesRepository) : ViewModel() {
    private val notesListLiveData = MutableLiveData<ArrayList<Note>>()
    private val progressLiveData = MutableLiveData<Boolean>()

    fun fetchNotes() {
        progressLiveData.value = true
        repository.getNotes { value: ArrayList<Note> ->
            notesListLiveData.value = value
            progressLiveData.setValue(false)
        }
    }

    fun getNotesListLiveData(): LiveData<List<AdapterItem>> {
        return Transformations.map(notesListLiveData) { input: ArrayList<Note> ->
            val output = ArrayList<AdapterItem>()
            input.sortWith { o1, o2 -> o1.creationDateTime.compareTo(o2.creationDateTime) }
            for (note in input) {
                output.add(NoteAdapterItem(note))
            }
            output
        }
    }

    fun getProgressLiveData(): LiveData<Boolean> {
        return progressLiveData
    }

    fun addNewNote() {
        progressLiveData.value = true
        repository.addNewNote { value: Note ->
            val currentNotes = notesListLiveData.value!!
            currentNotes.add(value)
            notesListLiveData.postValue(currentNotes)
            progressLiveData.setValue(false)
        }
    }

    fun clearNotes() {
        progressLiveData.value = true
        repository.clearNotes { voidValue: Void? ->
            // TODO: clear repository? Danger!
            notesListLiveData.postValue(ArrayList())
            progressLiveData.setValue(false)
        }
    }

    fun deleteAtPosition(contextMenuItemPosition: Int) {
        progressLiveData.value = true
        // TODO: repository delete
        repository.addNewNote { value: Note? ->
            val currentNotes = ArrayList(notesListLiveData.value)
            currentNotes.removeAt(contextMenuItemPosition)
            notesListLiveData.postValue(currentNotes)
            progressLiveData.setValue(false)
        }
    }
}