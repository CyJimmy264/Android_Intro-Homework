package ru.cj264.geekbrains.android_intro.homework.domain;

public interface Callback<T> {
    void onResult(T value);
}
