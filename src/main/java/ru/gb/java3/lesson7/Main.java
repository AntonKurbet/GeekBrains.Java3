package ru.gb.java3.lesson7;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
//    1. Создать класс, который может выполнять «тесты», в качестве тестов выступают классы
//        с наборами методов с аннотациями @Test. Для этого у него должен быть статический метод
//        start(), которому в качестве параметра передается или объект типа Class, или имя класса.
//        Из «класса-теста» вначале должен быть запущен метод с аннотацией @BeforeSuite, если такой
//        имеется, далее запущены методы с аннотациями @Test, а по завершению всех тестов – метод с
//        аннотацией @AfterSuite. К каждому тесту необходимо также добавить приоритеты
//        (int числа от 1 до 10), в соответствии с которыми будет выбираться порядок их выполнения,
//        если приоритет одинаковый, то порядок не имеет значения.
//        Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в единственном
//        экземпляре, иначе необходимо бросить RuntimeException при запуске «тестирования».
//
//    Это домашнее задание никак не связано с темой тестирования через JUnit и
//    использованием этой библиотеки, то есть проект пишется с нуля.

        try {
            TestAnnot.start("ru.gb.java3.lesson7.TestAnnot");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }

//    2. Написать программу для проверки ДЗ
//    (Проанализировать папку с компилированными классами и вызвать методы, проверить результат)

        try {
            Files.list(new File("").toPath())
                    .filter(new Predicate<Path>() {
                        @Override
                        public boolean test(Path path) {
                            return path.toString().endsWith(".class");
                        }
                    })
                    .forEach(path -> {
                        try {
                            CheckClass(path.toUri());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

// 3. Заполнить матрицу по спирали
//        2 варианта 1й закомментирован
        int[][] matrix = new int[5][7];
        fillSpiral(matrix);
        printMatrix(matrix);
    }

    private static void printMatrix(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.printf("%3d", m[i][j]);
            }
            System.out.println();
        }
    }

    private static void fillSpiral(int[][] m) {
        // int c = 1, max = m.length * m[0].length; // VAR1
        int c = 0, max = m.length * m[0].length;
        for (int q = 0; q < Math.max(m.length / 2, m[0].length / 2); q++) {
        //     c = fillSquare(m, c, max, q,q,m.length - q - 1, m[0].length - q - 1); // VAR1
            c = fillSquare2(m, c, max, q,q,m.length - q - 1, m[0].length - q - 1);
        }
    }

//    VAR1
//    private static int fillSquare(int[][] m, int c, int max, int x1, int y1, int x2, int y2) {
//        for (int i = y1; i <=y2; i++) {
//            m[x1][i] = c++;
//        }
//        if (c > max) return max;
//        for (int j = x1 + 1; j <= x2; j++) {
//            m[j][y2] = c++;
//        }
//        if (c > max) return max;
//        for (int i = y2 - 1; i >= y1; i--) {
//            m[x2][i] = c++;
//        }
//        if (c > max) return max;
//        for (int j = x2 - 1; j > x1; j--) {
//            m[j][y1] = c++;
//        }
//        return c;
//    }


    private static int fillSquare2(int[][] m, int c, int max, int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int r = dx + dy;

        for (int y = 0; y < dy; y++) {
            c++;
            m[x1][y1 + y] = c;
            if (c + r > max) return max;
            m[x2][y2 - y] = c + r;
        }
        for (int x = 0; x < dx; x++) {
            c++;
            m[x1 + x][y2] = c;
            if (c + r > max) return max;
            m[x2 - x][y1] = c + r;
        }

        c += r;
        return c;
    }

    private static void CheckClass(URI path) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class c = URLClassLoader.newInstance(new URL[]{path.toURL()}).loadClass("ru.gb.java3.lesson7.TestAnnot");
        Constructor constructor = c.getConstructor();
        Object o = constructor.newInstance();

        if ((Integer) c.getDeclaredMethod("testMethod1").invoke(o) == 1)
            if ((Integer) c.getDeclaredMethod("testMethod2").invoke(o) == 2)
                if ((Integer) c.getDeclaredMethod("testMethod3").invoke(o) == 3) {
                    System.out.println("Test OK");
                    return;
                }
        System.out.println("Test failed");
    }

}
