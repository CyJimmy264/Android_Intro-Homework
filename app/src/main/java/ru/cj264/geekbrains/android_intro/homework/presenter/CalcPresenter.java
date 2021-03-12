package ru.cj264.geekbrains.android_intro.homework.presenter;

import ru.cj264.geekbrains.android_intro.homework.domain.Calc;
import ru.cj264.geekbrains.android_intro.homework.domain.CalcInterface;
import ru.cj264.geekbrains.android_intro.homework.ui.MainActivity;

public class CalcPresenter {
    MainActivity mainActivity;
    CalcInterface calc = new Calc();

    String digits;

    public CalcPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void numberPressed(String digit) {
        digits += digit;
    }
}
