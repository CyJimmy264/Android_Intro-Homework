package ru.cj264.geekbrains.android_intro.homework1;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EditTextExamplesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_examples);

        Button button = findViewById(R.id.backButton);
        button.setOnClickListener(v -> this.finish());
    }
}