package ru.cj264.geekbrains.android_intro.homework.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Optional;

import ru.cj264.geekbrains.android_intro.homework.R;
import ru.cj264.geekbrains.android_intro.homework.domain.MockNotesRepository;
import ru.cj264.geekbrains.android_intro.homework.domain.Note;
import ru.cj264.geekbrains.android_intro.homework.domain.NotesRepository;

public class NoteFragment extends Fragment {

    public static final String ARG_NOTE_ID = "noteId";
    private Note note;

    private final NotesRepository repository = MockNotesRepository.INSTANCE;


    public static NoteFragment newInstance(String noteId) {
        NoteFragment fragment = new NoteFragment();

        Bundle args = new Bundle();
        args.putString(ARG_NOTE_ID, noteId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String noteId = getArguments().getString(ARG_NOTE_ID);
            note = null;
            repository.getNotes().stream()
                    .filter(note -> note.getId().equals(noteId)).findFirst()
                    .ifPresent(value -> note = value);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        ((TextInputEditText) view.findViewById(R.id.note_title)).setText(note.getTitle());
        ((TextInputEditText) view.findViewById(R.id.note_description)).setText(note.getDescription());
        return view;
    }
}