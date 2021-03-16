package ru.cj264.geekbrains.android_intro.homework.domain;

import ru.cj264.geekbrains.android_intro.homework.domain.operation.Operation;

public class Calc implements ICalc {

    @Override
    public double binaryOperation(double arg1, double arg2, Operation operation) {
        return operation.eval(arg1, arg2);
    }
}
