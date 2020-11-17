package ru.gb.java3.lesson6;

import java.io.Serializable;

public class SerObject implements Serializable {
    int intField;
    float floatField;
    String stringField;

    public SerObject(int i, String s, float f) {
        this.intField = i;
        this.stringField = s;
        this.floatField = f;
    }

    public String info() {
        return String.format("%d %s %8.2f",intField,stringField,floatField);
    }
}
