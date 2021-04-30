package ru.cj264.geekbrains.android_intro.homework.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.cj264.geekbrains.android_intro.homework.R
import ru.cj264.geekbrains.android_intro.homework.ui.notes_list.NotesListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_app, NotesListFragment(), NotesListFragment.TAG)
                    .commit()
        }
    }
}