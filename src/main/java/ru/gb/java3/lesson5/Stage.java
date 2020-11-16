package ru.gb.java3.lesson5;

import ru.gb.java3.lesson5.Car;

public abstract class Stage {
    protected int length;
    protected String description;
    public String getDescription() {
        return description;
    }
    public abstract void go(Car c);
}
