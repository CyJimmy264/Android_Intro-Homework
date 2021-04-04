package ru.cj264.geekbrains.android_intro.homework.ui.notes_list;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ru.cj264.geekbrains.android_intro.homework.R;
import ru.cj264.geekbrains.android_intro.homework.ui.NoteFragment;

public class NotesListFragment extends Fragment {

    public static final String TAG = "NotesListFragment";
    public static final String STATE_CURRENT_NOTE = "CurrentNote";
    private String currentNoteId;

    private NotesListViewModel notesListViewModel;
    private NotesListAdapter notesListAdapter;

    private boolean isLandscape;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notesListViewModel = ViewModelProviders.of(this, new NotesListViewModelFactory())
                .get(NotesListViewModel.class);
        notesListViewModel.fetchNotes();

        notesListAdapter = new NotesListAdapter();
        notesListAdapter.setOnNoteClicked(note -> {
            currentNoteId = note.getId();
            showNote();
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
        notesList.setLayoutManager(new LinearLayoutManager(requireContext()));

        ProgressBar progressBar = view.findViewById(R.id.progress);

        notesListViewModel.getNotesLiveData()
                .observe(getViewLifecycleOwner(), notes -> {
                    notesListAdapter.clear();
                    notesListAdapter.addItems(notes);
                    notesListAdapter.notifyDataSetChanged();
                });

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
}