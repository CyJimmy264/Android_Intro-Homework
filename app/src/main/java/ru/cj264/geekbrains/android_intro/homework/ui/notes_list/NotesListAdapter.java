package ru.cj264.geekbrains.android_intro.homework.ui.notes_list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import ru.cj264.geekbrains.android_intro.homework.R;
import ru.cj264.geekbrains.android_intro.homework.domain.Note;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> {

    List<Note> items = new ArrayList<>();

    public void addItems(List<Note> items) {
        this.items.addAll(items);
    }

    public void clear() {
        items.clear();
    }

    @NonNull
    @Override
    public NotesListAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);

        return new NoteViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesListAdapter.NoteViewHolder holder, int position) {
        Note item = items.get(position);

        holder.getNoteTitle().setText(item.getTitle());

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm");
        ZonedDateTime dateTime = ZonedDateTime.of(
                item.getCreationDateTime(),
                ZoneId.of(TimeZone.getDefault().getID())
        );
        holder.getNoteCreationDate().setText(dateTime.format(dateTimeFormatter));

        Glide.with(holder.getNoteImage())
                .load(item.getImageUrl())
                .into(holder.getNoteImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteTitle;
        private final TextView noteCreationDate;
        private final ImageView noteImage;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.note_title);
            noteCreationDate = itemView.findViewById(R.id.note_creation_date);
            noteImage = itemView.findViewById(R.id.note_image);
        }

        public TextView getNoteTitle() {
            return noteTitle;
        }

        public TextView getNoteCreationDate() {
            return noteCreationDate;
        }

        public ImageView getNoteImage() {
            return noteImage;
        }
    }
}
