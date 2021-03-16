package ru.cj264.geekbrains.android_intro.homework.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.cj264.geekbrains.android_intro.homework.R;
import ru.cj264.geekbrains.android_intro.homework.presenter.CalcPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ICalcView {

    private final CalcPresenter calcPresenter = new CalcPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_0).setOnClickListener(this);
        findViewById(R.id.button_1).setOnClickListener(this);
        findViewById(R.id.button_2).setOnClickListener(this);
        findViewById(R.id.button_3).setOnClickListener(this);
        findViewById(R.id.button_4).setOnClickListener(this);
        findViewById(R.id.button_5).setOnClickListener(this);
        findViewById(R.id.button_6).setOnClickListener(this);
        findViewById(R.id.button_7).setOnClickListener(this);
        findViewById(R.id.button_8).setOnClickListener(this);
        findViewById(R.id.button_9).setOnClickListener(this);

        findViewById(R.id.button_plus).setOnClickListener(this);
        findViewById(R.id.button_minus).setOnClickListener(this);
        findViewById(R.id.button_multiply).setOnClickListener(this);
        findViewById(R.id.button_divide).setOnClickListener(this);
        findViewById(R.id.button_point).setOnClickListener(this);
        findViewById(R.id.button_equals).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            String buttonSym = ((Button) v).getTag().toString();
            calcPresenter.buttonPressed(buttonSym);
        }
    }

    public void displayResult(String expression) {
        ((TextView) findViewById(R.id.textView)).setText(expression);
    }
}