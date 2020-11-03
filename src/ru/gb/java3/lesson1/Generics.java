package ru.gb.java3.lesson1;

import com.sun.applet2.AppletParameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Generics {
//1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
    private static <T> void swap (T[] arr, int index1, int index2) {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

//2. Написать метод, который преобразует массив в ArrayList;
    private static <T> List<T> toArrayList (T[] arr) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            result.add(arr[i]);
        }
        return result;
    }

//3. Большая задача:
//    a. Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)
//    b. Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта,
//       поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
//    c. Для хранения фруктов внутри коробки можете использовать ArrayList;
//    d. Сделать метод getWeight() который высчитывает вес коробки, зная количество фруктов и вес одного фрукта
//       (вес яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах);
//    e. Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку с той, которую подадут
//       в compare в качестве параметра, true - если их веса равны, false в противном случае(коробки с яблоками мы
//       можем сравнивать с коробками с апельсинами);
//    f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку(помним про сортировку
//       фруктов, нельзя яблоки высыпать в коробку с апельсинами), соответственно в текущей коробке фруктов не остается,
//       а в другую перекидываются объекты, которые были в этой коробке;
//    g. Не забываем про метод добавления фрукта в коробку.

    public static void main(String[] args) {
        String[] arr = {"one","two","three"};
        System.out.println("Initial array = " + Arrays.toString(arr));
        swap(arr,1,2);
        System.out.println("Changed array = " + Arrays.toString(arr));

        System.out.println("ArrayList = " + toArrayList(arr).toString());

        Box<Apple> boxOfApples = new Box<>();
        boxOfApples.put(new Apple());
        boxOfApples.put(new Apple());
        boxOfApples.put(new Apple());

        Box<Orange> boxOfOranges = new Box<>();
        boxOfOranges.put(new Orange());
        boxOfOranges.put(new Orange());

        System.out.printf("Apples weight: %5.2f, Oranges weight: %5.2f\n",
                boxOfApples.getWeight(),boxOfOranges.getWeight());
        System.out.println(boxOfApples.compare(boxOfOranges) ? "Boxes has equal weight" : "Boxes weight is different");


        Box<Orange> box2 = new Box<>();
        System.out.println("1st box:" + boxOfOranges.toString());
        System.out.println("2nd box:" + box2.toString());
        boxOfOranges.moveTo(box2);
        System.out.println("1st box:" + boxOfOranges.toString());
        System.out.println("2nd box:" + box2.toString());
    }
}
