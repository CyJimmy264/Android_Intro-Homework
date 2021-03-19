package ru.cj264.geekbrains.android_intro.homework.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.cj264.geekbrains.android_intro.homework.R;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String NameSharedPreference = "CALC_PREFS";
    private static final String AppTheme = "APP_THEME";

    protected static final int AppThemeLightCodeStyle = 1;
    protected static final int AppThemeCodeStyle = 2;
    protected static final int AppThemeDarkCodeStyle = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.AppTheme));
    }

    private int getAppTheme(int codeStyle) {
        return codeStyleToStyleId(getCodeStyle(codeStyle));
    }

    protected int getCodeStyle(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        return sharedPref.getInt(AppTheme, codeStyle);
    }

    protected void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(AppTheme, codeStyle);
        editor.apply();
    }

    private int codeStyleToStyleId(int codeStyle) {
        switch(codeStyle){
            case AppThemeLightCodeStyle:
                return R.style.AppThemeLight;
            case AppThemeDarkCodeStyle:
                return R.style.AppThemeDark;
            default:
                return R.style.AppTheme;
        }
    }
}
