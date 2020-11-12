package ru.gb.java3.lesson4;

public class UserTask  extends Thread{
    private final String taskKind;
    private final String taskName;
    private final MFU mfu;

    UserTask(MFU mfu, String taskKind, String taskName) {
        this.mfu = mfu;
        this.taskKind = taskKind;
        this.taskName = taskName;
    }

    @Override
    public void run() {
        switch (taskKind) {
            case "p": mfu.printTask(taskName);
                break;
            case "s": mfu.scanTask(taskName);
                break;
            case "c": mfu.copyTask(taskName);
                break;
            default:
        }
    }
}
