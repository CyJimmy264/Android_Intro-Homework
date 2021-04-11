package ru.cj264.geekbrains.android_intro.homework.ui.notes_list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import ru.cj264.geekbrains.android_intro.homework.R;
import ru.cj264.geekbrains.android_intro.homework.domain.Note;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> {

    private final List<AdapterItem> items = new ArrayList<>();

    private OnNoteClicked onNoteClicked;
    private OnNoteLongClicked onNoteLongClicked;

    private final Fragment fragment;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm");

    public NotesListAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setItems(List<AdapterItem> items) {
        DiffUtil.Callback callback = new DiffUtilCallback(this.items, items);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);
        diffResult.dispatchUpdatesTo(this);
        this.items.clear();
        this.items.addAll(items);
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
        Note note = ((NoteAdapterItem ) items.get(position)).getNote();

        holder.getNoteTitle().setText(note.getTitle());

        ZonedDateTime dateTime = ZonedDateTime.of(
                note.getCreationDateTime(),
                ZoneId.of(TimeZone.getDefault().getID())
        );
        holder.getNoteCreationDate().setText(dateTime.format(dateTimeFormatter));

        Glide.with(holder.getNoteImage())
                .load(note.getImageUrl())
                .into(holder.getNoteImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public OnNoteClicked getOnNoteClicked() {
        return onNoteClicked;
    }

    public void setOnNoteClicked(OnNoteClicked onNoteClicked) {
        this.onNoteClicked = onNoteClicked;
    }

    public OnNoteLongClicked getOnNoteLongClicked() {
        return onNoteLongClicked;
    }

    public void setOnNoteLongClicked(OnNoteLongClicked onNoteLongClicked) {
        this.onNoteLongClicked = onNoteLongClicked;
    }


    public interface OnNoteClicked {
        void onNoteClicked(Note note);
    }

    public interface OnNoteLongClicked {
        void onNoteLongClicked(View itemView, int position, Note note);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteTitle;
        private final TextView noteCreationDate;
        private final ImageView noteImage;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            fragment.registerForContextMenu(itemView);

            noteTitle = itemView.findViewById(R.id.note_title);
            noteCreationDate = itemView.findViewById(R.id.note_creation_date);
            noteImage = itemView.findViewById(R.id.note_image);

            itemView.setOnClickListener(v -> {
                Note adapterPositionNote = ((NoteAdapterItem) items.get(getAdapterPosition())).getNote();
                if (getOnNoteClicked() != null) {
                    getOnNoteClicked().onNoteClicked(adapterPositionNote);
                }
            });

            itemView.setOnLongClickListener(v -> {
                Note adapterPositionNote = ((NoteAdapterItem) items.get(getAdapterPosition())).getNote();
                if (getOnNoteLongClicked() != null) {
                    getOnNoteLongClicked().onNoteLongClicked(itemView, getAdapterPosition(),
                            adapterPositionNote);
                }

                return true;
            });
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
