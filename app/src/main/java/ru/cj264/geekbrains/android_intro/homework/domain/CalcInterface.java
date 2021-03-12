package ru.cj264.geekbrains.android_intro.homework.domain;

import ru.cj264.geekbrains.android_intro.homework.domain.operation.Operation;

public interface CalcInterface {
    double binaryOperation(double arg1, double arg2, Operation operation);
}
