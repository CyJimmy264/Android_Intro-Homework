package ru.cj264.geekbrains.android_intro.homework.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.google.android.material.radiobutton.MaterialRadioButton;

import ru.cj264.geekbrains.android_intro.homework.R;

public class ThemeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        initThemeChooser();
    }


    private void initThemeChooser() {
        initRadioButton(findViewById(R.id.radioButtonMaterialDark), AppThemeDarkCodeStyle);
        initRadioButton(findViewById(R.id.radioButtonMaterialLight), AppThemeLightCodeStyle);
        initRadioButton(findViewById(R.id.radioButtonMaterialLightDarkAction),
                AppThemeCodeStyle);

        RadioGroup rg = findViewById(R.id.radioButtons);
        ((MaterialRadioButton) rg.getChildAt(getCodeStyle(AppThemeCodeStyle))).setChecked(true);
    }

    private void initRadioButton(View button, final int codeStyle) {
        button.setOnClickListener(v -> {
            setAppTheme(codeStyle);
            recreate();
        });
    }
}