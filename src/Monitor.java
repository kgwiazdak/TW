import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    private static final int maximalNumberToConsumerOrProduce = 10;
    Buffor buffor = new Buffor();
    ReentrantLock lock = new ReentrantLock();
    Condition producentCanProduce = lock.newCondition();
    Condition consumerCanConsume = lock.newCondition();


    public void consume() throws InterruptedException {
        System.out.println("Consumer thread started");
        try {
            lock.lock();
            while (buffor.getNumber() < 0) {
                consumerCanConsume.await();
            }
            int numberToConsume = getRandomNumber(1, maximalNumberToConsumerOrProduce);
            int currentNumber = buffor.getNumber();
            buffor.setNumber(currentNumber+numberToConsume);
            producentCanProduce.signal();
        } finally {
            lock.unlock();
        }
        System.out.println("Consumer thread ended");
    }

    public void produce() throws InterruptedException {
        System.out.println("Producer thread started");
        try {
            lock.lock();
            while (buffor.getNumber() < 2 * maximalNumberToConsumerOrProduce) {
                producentCanProduce.await();
            }
            int numberToProduce = getRandomNumber(1, maximalNumberToConsumerOrProduce);
            int currentNumber = buffor.getNumber();
            buffor.setNumber(currentNumber+numberToProduce);
            consumerCanConsume.signal();
        } finally {
            lock.unlock();
        }
        System.out.println("Producer thread ended");
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
