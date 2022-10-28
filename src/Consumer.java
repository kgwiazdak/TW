public class Consumer extends Thread {
    Monitor monitor;
    int repetitionRate;

    public Consumer(Monitor monitor, int repetitionRate) {
        this.monitor = monitor;
        this.repetitionRate = repetitionRate;
    }

    @Override
    public void run() {
        try {
            for (; ; )
                monitor.consume();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
