package ru.cj264.geekbrains.android_intro.homework.presenter;

import ru.cj264.geekbrains.android_intro.homework.domain.Calc;
import ru.cj264.geekbrains.android_intro.homework.domain.ICalc;
import ru.cj264.geekbrains.android_intro.homework.ui.MainActivity;

public class CalcPresenter {
    private final MainActivity mainActivity;
    ICalc calc = new Calc();

    String expression = "";

    public CalcPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void buttonPressed(String symbol) {
        if (symbol.equals("=")) {
        } else {
            expression += symbol;

            mainActivity.displayResult(expression);
        }
    }
}
