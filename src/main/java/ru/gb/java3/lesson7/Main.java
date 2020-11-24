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
