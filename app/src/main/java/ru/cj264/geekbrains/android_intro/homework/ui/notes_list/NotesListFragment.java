package ru.cj264.geekbrains.android_intro.homework.ui.notes_list;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;

import ru.cj264.geekbrains.android_intro.homework.R;
import ru.cj264.geekbrains.android_intro.homework.ui.note.NoteFragment;
import ru.cj264.geekbrains.android_intro.homework.ui.notes_list.adapter.NotesListAdapter;

public class NotesListFragment extends Fragment implements DeleteDialogFragment.OnClicked {

    public static final String TAG = "NotesListFragment";
    public static final String STATE_CURRENT_NOTE = "CurrentNote";
    private String currentNoteId;

    private NotesListViewModel notesListViewModel;
    private NotesListAdapter notesListAdapter;

    private boolean isLandscape;
    private int contextMenuItemPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notesListViewModel = ViewModelProviders.of(this, new NotesListViewModelFactory())
                .get(NotesListViewModel.class);
        if (savedInstanceState == null) {
            notesListViewModel.fetchNotes();
        }

        notesListAdapter = new NotesListAdapter(this);
        notesListAdapter.setOnNoteClicked(note -> {
            currentNoteId = note.getId();
            showNote();
        });

        notesListAdapter.setOnNoteLongClicked((itemView, position, note) -> {
            contextMenuItemPosition = position;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                itemView.showContextMenu(10, 10);
            } else {
                itemView.showContextMenu();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView notesList = view.findViewById(R.id.notes_list);
        notesList.setAdapter(notesListAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        notesList.setLayoutManager(linearLayoutManager);

        ProgressBar progressBar = view.findViewById(R.id.progress);

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_add_new) {
                notesListViewModel.addNewNote();
            } else if (item.getItemId() == R.id.action_clear) {
                notesListViewModel.clearNotes();
            }
            return true;
        });

        notesListViewModel.getNotesListLiveData()
                .observe(getViewLifecycleOwner(), notes -> notesListAdapter.setItems(notes));

        notesListViewModel.getProgressLiveData()
                .observe(getViewLifecycleOwner(), isVisible -> {
                    if (isVisible) {
                        progressBar.setVisibility(ProgressBar.VISIBLE);
                    } else {
                        progressBar.setVisibility(ProgressBar.GONE);
                    }
                });
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentNoteId = savedInstanceState.getString(STATE_CURRENT_NOTE);
        } else {
            currentNoteId = "1"; // Objects.requireNonNull(notesListViewModel.getNotesLiveData().getValue())                    .get(0).getId();
        }

        if (isLandscape) {
            showNote();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(STATE_CURRENT_NOTE, currentNoteId);
        super.onSaveInstanceState(outState);
    }

    private void showNote() {
        if (isLandscape) {
            showLandNote();
        } else {
            showPortNote();
        }
    }

    private void showPortNote() {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_app, NoteFragment.newInstance(currentNoteId), NoteFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack("showPortNote")
                .commit();
    }

    private void showLandNote() {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.note, NoteFragment.newInstance(currentNoteId))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_notes_list, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            new DeleteDialogFragment().show(getChildFragmentManager(), DeleteDialogFragment.TAG);
            return true;
        }
        if (item.getItemId() == R.id.action_date_picker) {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().build();
            datePicker.addOnPositiveButtonClickListener(selection -> Log.d(TAG, "selection: " + selection.toString()));
            datePicker.show(getChildFragmentManager(), "MaterialDatePicker");
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onDeleteClickedYes() {
        notesListViewModel.deleteAtPosition(contextMenuItemPosition);
    }

    @Override
    public void onDeleteClickedNo() {
        Toast.makeText(requireContext(), R.string.no, Toast.LENGTH_LONG).show();
    }
}
