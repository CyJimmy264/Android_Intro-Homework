package ru.cj264.geekbrains.android_intro.homework.ui.notes_list;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;
import java.util.Objects;

import ru.cj264.geekbrains.android_intro.homework.R;
import ru.cj264.geekbrains.android_intro.homework.domain.Note;
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

        notesListViewModel = ViewModelProviders.of(this).get(NotesListViewModel.class);
        notesListViewModel.fetchNotes();

        notesListAdapter = new NotesListAdapter();
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

        notesListViewModel.getNotesLiveData()
                .observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
                    @Override
                    public void onChanged(List<Note> notes) {
                        notesListAdapter.clear();
                        notesListAdapter.addItems(notes);
                        notesListAdapter.notifyDataSetChanged();
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
            currentNoteId = Objects.requireNonNull(notesListViewModel.getNotesLiveData().getValue())
                    .get(0).getId();
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



    private int dpToPx(int dp) {
        Resources r = Objects.requireNonNull(getContext()).getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()
        );
    }

//            tv.setOnClickListener(v -> {
//                currentNoteId = notes.get(fi).getId();
//                showNote();
//            });

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