package ru.cj264.geekbrains.android_intro.homework.domain;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.cj264.geekbrains.android_intro.homework.domain.operation.DoubleOperation;
import ru.cj264.geekbrains.android_intro.homework.domain.operation.Operation;

public class Calc implements ICalc {

    @Override
    public double binaryOperation(double arg1, double arg2, Operation operation) {
        return operation.eval(arg1, arg2);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public double evaluateExpression(String expression) {
        double result = 0;


        Matcher m = Pattern.compile("(-?\\d*\\.?\\d*)([-+*/])(\\d*\\.?\\d*)").matcher(expression);

        if (m.find()) {
            double number1 = Double.parseDouble(Objects.requireNonNull(m.group(1)));
            double number2 = Double.parseDouble(Objects.requireNonNull(m.group(3)));
            String operation = m.group(2);

            switch (Objects.requireNonNull(operation)) {
                case "+":
                    result = binaryOperation(number1, number2, DoubleOperation::add);
                    break;
                case "-":
                    result = binaryOperation(number1, number2, DoubleOperation::sub);
                    break;
                case "*":
                    result = binaryOperation(number1, number2, DoubleOperation::mul);
                    break;
                case "/":
                    result = binaryOperation(number1, number2, DoubleOperation::div);
                    break;
            }
        }

        return result;
    }
}
