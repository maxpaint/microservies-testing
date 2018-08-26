package com.intellias.greeting.service;

public interface Greeting<T> {

    T greeting(String email);
}
