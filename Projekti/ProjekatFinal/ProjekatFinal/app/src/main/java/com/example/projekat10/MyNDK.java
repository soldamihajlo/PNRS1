package com.example.projekat10;

public class MyNDK {

    static {
        System.loadLibrary("promeni");
    }

    public native double promeni(double tempValue,boolean farah);
}
