public class Producer extends Thread {
    Monitor monitor;
    int repetitionRate;

    public Producer(Monitor monitor, int repetitionRate) {
        this.monitor = monitor;
        this.repetitionRate = repetitionRate;
    }

    @Override
    public void run() {
        try {
            for (; ; )
                monitor.produce();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
