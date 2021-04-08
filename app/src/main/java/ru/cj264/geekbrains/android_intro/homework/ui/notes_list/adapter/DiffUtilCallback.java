package ru.cj264.geekbrains.android_intro.homework.ui.notes_list.adapter;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class DiffUtilCallback extends DiffUtil.Callback {

    private final List<AdapterItem> oldNotes;
    private final List<AdapterItem> newNotes;

    public DiffUtilCallback(List<AdapterItem> oldNotes, List<AdapterItem> newNotes) {
        this.oldNotes = oldNotes;
        this.newNotes = newNotes;
    }

    @Override
    public int getOldListSize() {
        return oldNotes.size();
    }

    @Override
    public int getNewListSize() {
        return newNotes.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldNotes.get(oldItemPosition).uniqueTag()
                .equals(newNotes.get(newItemPosition).uniqueTag());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldNotes.get(oldItemPosition)
                .equals(newNotes.get(newItemPosition));
    }
}
