package ru.cj264.geekbrains.android_intro.homework.presenter;

import java.util.Locale;

import ru.cj264.geekbrains.android_intro.homework.domain.Calc;
import ru.cj264.geekbrains.android_intro.homework.domain.ICalc;
import ru.cj264.geekbrains.android_intro.homework.ui.ICalcView;

public class CalcPresenter {
    private final ICalcView view;
    private final ICalc calc = new Calc();

    private String expression = "";

    public CalcPresenter(ICalcView view) {
        this.view = view;
    }

    public static String fmt(double d) {
        if(d == (long) d) {
            return String.format(Locale.US, "%d", (long) d);
        } else {
            return String.format(Locale.US, "%s", d);
        }
    }

    public void buttonPressed(String symbol) {
        if (symbol.equals("=")) {
            expression = fmt(calc.evaluateExpression(expression));
        } else {
            expression += symbol;
        }
        view.displayResult(expression);
    }
}
