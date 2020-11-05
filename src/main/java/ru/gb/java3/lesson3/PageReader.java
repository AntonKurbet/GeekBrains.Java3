package main.java.ru.gb.java3.lesson3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class PageReader {
    private static final int PAGE_SIZE = 1800;
    //    3. Написать консольное приложение, которое умеет постранично читать текстовые файлы (размером > 10 mb).
    //    Вводим страницу (за страницу можно принять 1800 символов), программа выводит ее в консоль.
    //    Контролируем время выполнения: программа не должна загружаться дольше 10 секунд,
    //    а чтение – занимать свыше 5 секунд.

    private static RandomAccessFile book;
    private static byte[] buffer = new byte[PAGE_SIZE];
    private static long timer = 0;

    static void setBook(String filename) throws FileNotFoundException {
        try {
            book = new RandomAccessFile(filename, "r");
        } catch (FileNotFoundException e) {
            System.out.printf("File %s not found\n",filename);
            throw e;
        }
    }

    static void gotoPage(int pageNum) {
        try {
            book.seek((pageNum - 1) * PAGE_SIZE);
        } catch (IOException e) {
            System.out.printf("No %d page\n",pageNum);
        }
    }

    static void readPage() {
        try {
            book.readFully(buffer);
        } catch (IOException e) {
            System.out.println("Read error");
        }
    }

    static void printBuffer() {
        String str = new String(buffer);
        System.out.println(str);
    }

    static void startTimer() { timer = System.currentTimeMillis();}
    static long stopTimer() { return System.currentTimeMillis() - timer;}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input filename :");
        String fileName = scanner.nextLine();
        startTimer();
        try {
            setBook(fileName);
        }
        catch (FileNotFoundException e) {
            stopTimer();
        }
        System.out.printf("%d msec\n",stopTimer());
        int page;
        do {
            System.out.println("Input page number (0 - exit) :");
            page = scanner.nextInt();
            if (page > 0) {
                startTimer();
                gotoPage(page);
                readPage();
                printBuffer();
                System.out.printf("%d msec\n", stopTimer());
            }
        } while (page > 0);
    }

}
