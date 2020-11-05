package ru.gb.java3.lesson1;

import java.util.ArrayList;

public class Box <T extends Fruit> {
    private ArrayList<T> contents;

    Box() {
        contents = new ArrayList<>();
    }
    public void put(T fruit) {
        contents.add(fruit);
    }

    public float getWeight() {
        float result = 0;
        for (T f: contents) {
            result += f.weight;
        }
        return result;
    }

    public boolean compare (Box<? extends Fruit> otherBox) {
        return this.getWeight() == otherBox.getWeight();
    }

    public void moveTo(Box<T> otherBox) {
        if (otherBox == null) otherBox = new Box<>();
        for (T f: contents) {
            otherBox.put(f);
        }
        contents.clear();
    }

    @Override
    public String toString() {
        return contents.toString();
    }
}
