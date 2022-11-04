public class Consumer extends Thread {
    Monitor monitor;
    int repetitionRate;
    private static final int maximalNumberToConsumerOrProduce = 10;


    public Consumer(Monitor monitor, int repetitionRate) {
        this.monitor = monitor;
        this.repetitionRate = repetitionRate;
    }

    @Override
    public void run() {
        try {
            for (; ; ){
                monitor.consume(getRandomNumber());
                System.out.println("Consumed");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int getRandomNumber() {
        return (int) ((Math.random() * (maximalNumberToConsumerOrProduce - 1)) + 1);
    }
}
