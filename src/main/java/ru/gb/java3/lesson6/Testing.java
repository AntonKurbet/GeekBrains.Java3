package ru.gb.java3.lesson6;

import java.util.Arrays;

public class Testing {
//1. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
// Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
// идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку, иначе в методе
// необходимо выбросить RuntimeException. Написать набор тестов для этого метода (по 3-4 варианта входных данных).
// Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
//2. Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки или единицы,
// то метод вернет false; Написать набор тестов для этого метода (по 3-4 варианта входных данных).

    public int[] ProcessArray1(int[] a) {
        int i;
        for (i = a.length - 1; i >= 0 ; i--) {
            if (a[i] == 4) break;
        }
        if (i == -1) throw new RuntimeException("Array not contains 4");
        int[] result = Arrays.copyOfRange(a,i + 1,a.length);
        return result;
    }

    public boolean ProcessArray2(int[] a) {
        int i;
        for (i = 0; i < a.length; i++) {
            if ((a[i] == 4) || (a[i] == 1)) break;
        }
        return i != a.length;
    }
}
