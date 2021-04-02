package ru.cj264.geekbrains.android_intro.homework.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.cj264.geekbrains.android_intro.homework.R;
import ru.cj264.geekbrains.android_intro.homework.ui.notes_list.NotesListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_app, new NotesListFragment(), NotesListFragment.TAG)
                    .commit();
        }
    }
}