package ru.cj264.geekbrains.android_intro.homework.ui.note;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDateTime;

import ru.cj264.geekbrains.android_intro.homework.R;
import ru.cj264.geekbrains.android_intro.homework.databinding.FragmentNoteBinding;
import ru.cj264.geekbrains.android_intro.homework.domain.Note;

public class NoteFragment extends Fragment {

    public static final String TAG = "NoteFragment";
    public static final String ARG_NOTE_ID = "noteId";

    private FragmentNoteBinding binding;

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

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (requireActivity().getSupportFragmentManager().findFragmentByTag(NoteFragment.TAG) != null) {
                Log.d(TAG, "Landscape: NoteFragment");
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NoteViewModel.Factory factory = new NoteViewModel.Factory(
                requireArguments().getString(ARG_NOTE_ID)
        );

        final NoteViewModel model = new ViewModelProvider(this, factory)
                .get(NoteViewModel.class);

        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setNoteViewModel(model);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}