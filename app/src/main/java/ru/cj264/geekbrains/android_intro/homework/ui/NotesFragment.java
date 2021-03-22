package ru.cj264.geekbrains.android_intro.homework.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Locale;

import ru.cj264.geekbrains.android_intro.homework.R;
import ru.cj264.geekbrains.android_intro.homework.domain.Note;

public class NotesFragment extends Fragment {

    public static final String STATE_CURRENT_NOTE = "CurrentNote";
    private Note currentNote;
    private boolean isLandscape;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
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
            currentNote = savedInstanceState.getParcelable(STATE_CURRENT_NOTE);
        } else {
            currentNote = new Note();
        }

        if (isLandscape) {
            showLandNote(currentNote);
        }
    }

    private void initList(View view) {
        LinearLayout layoutView = view.findViewById(R.id.linear_layout);

        for (int i = 0; i < 120; i++) {
            TextView tv = new TextView(getContext());
            tv.setText(String.format(Locale.US, "Note %04d", i));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            layoutView.addView(tv);
            final int fi = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentNote = new Note();
                    showNote(currentNote);
                }
            });
        }

    }

    private void showNote(Note currentNote) {
        if (isLandscape) {
            showLandNote(currentNote);
        } else {
            showPortNote(currentNote);
        }
    }

    private void showPortNote(Note currentNote) {

    }

    private void showLandNote(Note currentNote) {

    }
}