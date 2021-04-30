package ru.cj264.geekbrains.android_intro.homework.ui.notes_list

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import ru.cj264.geekbrains.android_intro.homework.R
import ru.cj264.geekbrains.android_intro.homework.domain.Note
import ru.cj264.geekbrains.android_intro.homework.ui.note.NoteFragment
import ru.cj264.geekbrains.android_intro.homework.ui.notes_list.DeleteDialogFragment.OnClicked
import ru.cj264.geekbrains.android_intro.homework.ui.notes_list.adapter.AdapterItem
import ru.cj264.geekbrains.android_intro.homework.ui.notes_list.adapter.NoteAdapterItem
import ru.cj264.geekbrains.android_intro.homework.ui.notes_list.adapter.NotesListAdapter
import ru.cj264.geekbrains.android_intro.homework.ui.notes_list.adapter.NotesListAdapter.OnNoteClicked
import ru.cj264.geekbrains.android_intro.homework.ui.notes_list.adapter.NotesListAdapter.OnNoteLongClicked

class NotesListFragment : Fragment(), OnClicked {
    companion object {
        const val TAG = "NotesListFragment"
        const val STATE_CURRENT_NOTE = "CurrentNote"
    }

    private var currentNoteId: String? = null

    private lateinit var notesListViewModel: NotesListViewModel
    private lateinit var notesListAdapter: NotesListAdapter
    private var isLandscape = false
    private var contextMenuItemPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notesListViewModel = ViewModelProviders.of(this, NotesListViewModelFactory())
                .get(NotesListViewModel::class.java)

        if (savedInstanceState == null) {
            notesListViewModel.fetchNotes()
        }

        notesListAdapter = NotesListAdapter(this)
        notesListAdapter.onNoteClicked = OnNoteClicked { note: Note ->
            currentNoteId = note.id
            showNote()
        }
        notesListAdapter.onNoteLongClicked = OnNoteLongClicked { itemView: View, position: Int, _: Note? ->
            contextMenuItemPosition = position
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                itemView.showContextMenu(10f, 10f)
            } else {
                itemView.showContextMenu()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notesList: RecyclerView = view.findViewById(R.id.notes_list)
        notesList.adapter = notesListAdapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        notesList.layoutManager = linearLayoutManager
        val progressBar = view.findViewById<ProgressBar>(R.id.progress)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setOnMenuItemClickListener { item: MenuItem ->
            if (item.itemId == R.id.action_add_new) {
                notesListViewModel.addNewNote()
            } else if (item.itemId == R.id.action_clear) {
                notesListViewModel.clearNotes()
            }
            true
        }
        notesListViewModel.getNotesListLiveData()
                .observe(viewLifecycleOwner, { notes: List<AdapterItem?>? -> notesListAdapter.setItems(notes) })
        notesListViewModel.getProgressLiveData()
                .observe(viewLifecycleOwner, { isVisible: Boolean ->
                    if (isVisible) {
                        progressBar.visibility = ProgressBar.VISIBLE
                    } else {
                        progressBar.visibility = ProgressBar.GONE
                    }
                })
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        currentNoteId =
            savedInstanceState?.getString(STATE_CURRENT_NOTE) ?:
            (notesListViewModel.getNotesListLiveData().value?.get(0) as NoteAdapterItem?)?.note?.id

        if (isLandscape) {
            showNote()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(STATE_CURRENT_NOTE, currentNoteId)
        super.onSaveInstanceState(outState)
    }

    private fun showNote() {
        if (isLandscape) {
            showLandNote()
        } else {
            showPortNote()
        }
    }

    private fun showPortNote() {
        requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_app, NoteFragment.newInstance(currentNoteId), NoteFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack("showPortNote")
                .commit()
    }

    private fun showLandNote() {
        childFragmentManager.beginTransaction()
                .replace(R.id.note, NoteFragment.newInstance(currentNoteId))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val menuInflater = requireActivity().menuInflater
        menuInflater.inflate(R.menu.context_notes_list, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                DeleteDialogFragment().show(childFragmentManager, DeleteDialogFragment.TAG)
                return true
            }
            R.id.action_date_picker -> {
                val datePicker = MaterialDatePicker.Builder.datePicker().build()
                datePicker.addOnPositiveButtonClickListener { selection: Long -> Log.d(TAG, "selection: $selection") }
                datePicker.show(childFragmentManager, "MaterialDatePicker")
                return true
            }
        }

        return super.onContextItemSelected(item)
    }

    override fun onDeleteClickedYes() {
        notesListViewModel.deleteAtPosition(contextMenuItemPosition)
    }

    override fun onDeleteClickedNo() {
        Toast.makeText(requireContext(), R.string.no, Toast.LENGTH_LONG).show()
    }
}