public class Producer extends Thread {
    private static final int maximalNumberToConsumerOrProduce = 10;
    Monitor monitor;
    int repetitionRate;


    public Producer(Monitor monitor, int repetitionRate) {
        this.monitor = monitor;
        this.repetitionRate = repetitionRate;
    }

    @Override
    public void run() {
        try {
            for (; ; ) {
                monitor.produce(getRandomNumber());
                System.out.println("Produced");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int getRandomNumber() {
        return (int) ((Math.random() * (maximalNumberToConsumerOrProduce - 1)) + 1);
    }
}
