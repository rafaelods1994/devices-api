package com.github.rafael.devices;

import org.springframework.stereotype.Service;

@Service
public class InitialSetUp {

    public int add(int a, int b) {
        return a + b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public boolean isEven(int number) {
        return number % 2 == 0;
    }
}

