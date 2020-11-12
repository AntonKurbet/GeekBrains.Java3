package ru.gb.java3.lesson4;

public class LetterWriter extends Thread {
    private final char letter;
    static char nextLetter = 'A';
    static final Object lock = new Object();

    public LetterWriter(char letter) {
        super();
        this.letter = letter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            synchronized (lock) {
                while (nextLetter != letter) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(letter);
                setNextLetter();
                lock.notifyAll();
            }
        }
    }

    private void setNextLetter() {
        nextLetter = letter;
        if (++nextLetter > 'C')
            nextLetter = 'A';
    }
}
