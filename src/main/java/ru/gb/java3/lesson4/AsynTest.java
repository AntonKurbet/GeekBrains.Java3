package ru.gb.java3.lesson4;

public class AsynTest {

//3. Сделать клиен-серверное приложение. Передать по сети сеарилизованный объект.


    public static void main(String[] args) {
//1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз
//   (порядок – ABСABСABС). Используйте wait/notify/notifyAll.
        new LetterWriter('A').start();
        new LetterWriter('B').start();
        new LetterWriter('C').start();
//2. Создать MFU c функциями, сканирования, печати и ксерокопирования
        MFU mfu = new MFU();
        new UserTask(mfu,"p","pt01").start();
        new UserTask(mfu,"p","pt02").start();
        new UserTask(mfu,"p","pt03").start();
        new UserTask(mfu,"s","st01").start();
        new UserTask(mfu,"s","st02").start();
        new UserTask(mfu,"c","ct01").start();
        new UserTask(mfu,"p","pt04").start();
        new UserTask(mfu,"s","st02").start();
    }
}
