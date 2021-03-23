package ru.cj264.geekbrains.android_intro.homework.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import ru.cj264.geekbrains.android_intro.homework.R;
import ru.cj264.geekbrains.android_intro.homework.domain.MockNotesRepository;
import ru.cj264.geekbrains.android_intro.homework.domain.Note;
import ru.cj264.geekbrains.android_intro.homework.domain.NotesRepository;

public class NotesListFragment extends Fragment {

    public static final String STATE_CURRENT_NOTE = "CurrentNote";
    private String currentNoteId;

    private final NotesRepository repository = MockNotesRepository.INSTANCE;

    private boolean isLandscape;

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

        initList(view);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentNoteId = savedInstanceState.getString(STATE_CURRENT_NOTE);
        } else {
            currentNoteId = repository.getNotes().get(0).getId();
        }

        if (isLandscape) {
            showNote(currentNoteId);
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

    private void initList(View view) {
        LinearLayout layoutView = view.findViewById(R.id.linear_layout);

        ArrayList<Note> notes = repository.getNotes();

        for (int i = 0; i < notes.size(); i++) {
            TextView tv = new TextView(getContext());
            tv.setText(notes.get(i).getTitle());
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int dp10 = dpToPx(10);
            params.setMargins(dp10,dp10,dp10,dp10);
            tv.setLayoutParams(params);
            layoutView.addView(tv);
            final int fi = i;
            tv.setOnClickListener(v -> {
                currentNoteId = notes.get(fi).getId();
                showNote(currentNoteId);
            });
        }

    }

    private void showNote(String currentNoteId) {
        Optional<Note> optionalNote = repository.getNotes().stream()
                .filter(note -> note.getId().equals(currentNoteId)).findFirst();
        if (optionalNote.isPresent()) {
            if (isLandscape) {
                showLandNote(optionalNote.get());
            } else {
                showPortNote(optionalNote.get());
            }
        }
    }

    private void showPortNote(Note currentNote) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteActivity.class);
        intent.putExtra(NoteFragment.ARG_NOTE_ID, currentNoteId);
        startActivity(intent);
    }

    private void showLandNote(Note currentNote) {
        NoteFragment detail = NoteFragment.newInstance(currentNoteId);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}