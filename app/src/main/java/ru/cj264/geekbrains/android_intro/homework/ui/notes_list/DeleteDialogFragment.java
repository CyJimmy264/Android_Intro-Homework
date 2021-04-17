package ru.cj264.geekbrains.android_intro.homework.ui.notes_list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.cj264.geekbrains.android_intro.homework.R;

public class DeleteDialogFragment extends DialogFragment {
    public static String TAG = "DeleteDialogFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.delete_note)
                .setMessage(R.string.are_you_sure)
                .setIcon(R.drawable.ic_baseline_help_outline_24)
                .setPositiveButton(R.string.yes, (dialog1, which) -> {
                    if (getParentFragment() instanceof OnClicked) {
                        ((OnClicked) getParentFragment()).onDeleteClickedYes();
                    }
                })
                .setNegativeButton(R.string.no, (dialog1, which) -> {
                    if (getParentFragment() instanceof OnClicked) {
                        ((OnClicked) getParentFragment()).onDeleteClickedNo();
                    }
                })
                .create();
    }

    public interface OnClicked {
        void onDeleteClickedYes();
        void onDeleteClickedNo();
    }
}
