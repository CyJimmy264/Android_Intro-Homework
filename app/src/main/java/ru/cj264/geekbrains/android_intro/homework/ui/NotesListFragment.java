package ru.cj264.geekbrains.android_intro.homework.ui;

import android.content.Intent;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Objects;

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
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        return view;
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

    private void initList(View view) {
        LinearLayout layoutView = view.findViewById(R.id.linear_layout);

        ArrayList<Note> notes = repository.getNotes();

        int dp10 = dpToPx(10);
        int dp20 = dpToPx(20);

        for (int i = 0; i < notes.size(); i++) {
            TextView tv = new TextView(getContext());
            tv.setText(notes.get(i).getTitle());
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            tv.setBackgroundColor(Color.LTGRAY);
            tv.setPadding(dp10, dp10, dp10, dp10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(dp20,dp10,dp20,dp10);
            tv.setLayoutParams(params);
            layoutView.addView(tv);
            final int fi = i;
            tv.setOnClickListener(v -> {
                currentNoteId = notes.get(fi).getId();
                showNote();
            });
        }

    }

    private void showNote() {
        if (isLandscape) {
            showLandNote();
        } else {
            showPortNote();
        }
    }

    private void showPortNote() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteActivity.class);
        intent.putExtra(NoteFragment.ARG_NOTE_ID, currentNoteId);
        startActivity(intent);
    }

    private void showLandNote() {
        NoteFragment detail = NoteFragment.newInstance(currentNoteId);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.note, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}