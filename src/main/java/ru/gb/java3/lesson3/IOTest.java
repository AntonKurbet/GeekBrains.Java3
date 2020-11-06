package main.java.ru.gb.java3.lesson3;

import java.io.*;
import java.util.*;

public class IOTest {
    private static final int READ_BUFF_SIZE = 15;

//    1. Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
//    2. Последовательно сшить 5 файлов в один (файлы примерно 100 байт).
//    Может пригодиться следующая конструкция:
//    ArrayList<InputStream> al = new ArrayList<>(); ...
//    Enumeration<InputStream> e = Collections.enumeration(al);

    private static void writeRandomFile(String filename, int length) {

        try (FileOutputStream out = new FileOutputStream(filename)) {
            byte[] buff = new byte[length];
            for (int i = 0; i < length; i++) {
                buff[i] = (byte) (97 + Math.random() * 26f);
            }
            out.write(buff,0,length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] readFileBytes(String filename) {
        StringBuilder result = new StringBuilder();
        try (FileInputStream in = new FileInputStream(filename)) {
            byte[] buff = new byte[READ_BUFF_SIZE];
            int readBytes;
            while ((readBytes = in.read(buff)) > 0) {
                result.append(new String(buff, 0, readBytes, "UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString().getBytes();
    }

    private static void mergeFiles(String[] files, String filename) {
        ArrayList<InputStream> streams = new ArrayList<>();

        try {
            for (int i = 0; i < files.length; i++) {
                streams.add(new FileInputStream(files[i]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try (SequenceInputStream in = new SequenceInputStream(Collections.enumeration(streams));
             FileOutputStream out = new FileOutputStream(new File(filename))) {

            byte[] buff = new byte[READ_BUFF_SIZE * 10];
            int readBytes;
            while ((readBytes = in.read(buff)) > 0) {
                out.write(buff,0,readBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        writeRandomFile("randoms.txt",80);
        byte[] fromFile = readFileBytes("randoms.txt");
        System.out.println(Arrays.toString(fromFile));

        String[] files = new String[5];
        for (int i = 0; i < 5; i++) {
            files[i] = String.format("file%d.txt",i);
            writeRandomFile(files[i],100);
        }
        mergeFiles(files,"result.txt");
    }
}
