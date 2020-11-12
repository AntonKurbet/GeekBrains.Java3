package ru.gb.java3.lesson4;

public class MFU {
    private static final Object printLock = new Object();
    private static final Object scanLock = new Object();
    private static boolean printBusy = false;
    private static boolean scanBusy= false;

    public void printTask(String taskName) {
        synchronized (printLock) {
            while (printBusy) {
                try {
                    printLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            printBusy = true;
            System.out.println("Printing " + taskName);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Printed " + taskName);
            printBusy = false;
            printLock.notifyAll();
        }
    }

    public void scanTask(String taskName) {
        synchronized (scanLock) {
            while (scanBusy) {
                try {
                    scanLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            scanBusy = true;
            System.out.println("Scanning " + taskName);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Scanned " + taskName);
            scanBusy = false;
            scanLock.notifyAll();
        }
    }

    public void copyTask(String taskName) {
//        scanTask(taskName);
//        printTask(taskName);
        synchronized (scanLock) {
            while (scanBusy) {
                try {
                    scanLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            scanBusy = true;
            synchronized (printLock) {
                while (printBusy) {
                    try {
                        printLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                printBusy = true;
                System.out.println("Copying " + taskName);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Copied " + taskName);
                printBusy = false;
                printLock.notifyAll();
            }
            scanBusy = false;
            scanLock.notifyAll();
        }
    }
}
