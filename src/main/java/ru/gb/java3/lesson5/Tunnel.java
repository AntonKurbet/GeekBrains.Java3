package ru.gb.java3.lesson5;

import ru.gb.java3.lesson5.Car;
import ru.gb.java3.lesson5.Stage;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    public Semaphore sem;

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        sem = new Semaphore(2);
    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                sem.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                sem.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
