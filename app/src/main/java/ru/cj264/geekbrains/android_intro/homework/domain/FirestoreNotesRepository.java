package ru.cj264.geekbrains.android_intro.homework.domain;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class FirestoreNotesRepository implements NotesRepository {
    static final String TAG = "FirestoreNotesRepo";

    private static final String NOTES_COLLECTION = "notes";
    private static final String CREATION_DATE_TIME_FIELD = "creation_date_time";
    private static final String TITLE_FIELD = "title";
    private static final String DESCRIPTION_FIELD = "description";
    private static final String IMAGE_URL_FIELD = "image_url";


    public static final NotesRepository INSTANCE = new FirestoreNotesRepository();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        db.collection(NOTES_COLLECTION)
                .orderBy(CREATION_DATE_TIME_FIELD, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Note> notesList = new ArrayList<>();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());

                            Timestamp timestamp = document.getTimestamp(CREATION_DATE_TIME_FIELD);

                            Note note = new Note(
                                    document.getId(),
                                    document.getString(TITLE_FIELD),
                                    document.getString(DESCRIPTION_FIELD),
                                    Instant.ofEpochSecond(timestamp.getSeconds())
                                            .atZone(ZoneId.of(TimeZone.getDefault().getID()))
                                            .toLocalDateTime()
                                    );
                            note.setImageUrl(document.getString(IMAGE_URL_FIELD));

                            notesList.add(note);
                        }

                        callback.onResult(notesList);
                    } else {
                        Log.e(TAG, "Error getting documents.", task.getException());
                    }

                });
    }

    @Override
    public void clearNotes(Callback<Void> voidCallback) {

    }

    @Override
    public void addNewNote(Callback<Note> callback) {

    }
}
