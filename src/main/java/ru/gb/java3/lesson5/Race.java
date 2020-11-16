package ru.gb.java3.lesson5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Race {
    private ArrayList<Stage> stages;
    public CyclicBarrier cbStart;
    public CountDownLatch cdlStop;

    public ArrayList<Stage> getStages() { return stages; }

    public Race(int carsCount, Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
        cbStart = new CyclicBarrier(carsCount + 1);
        cdlStop = new CountDownLatch(carsCount);
    }
}
