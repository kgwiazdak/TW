import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    ReentrantLock lock = new ReentrantLock();
    Condition producentCanProduce = lock.newCondition();
    Condition consumerCanConsume = lock.newCondition();

    int number = 0;

    public int getNumber() {
        return number;
    }

    public void consume() throws InterruptedException {
        System.out.println("Consumer thread started");
        try {
            lock.lock();
            while (number==0) {
                consumerCanConsume.await();
            }
            number=0;
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
            while (number==1) {
                producentCanProduce.await();
            }
            number=1;
            consumerCanConsume.signal();
        } finally {
            lock.unlock();
        }
        System.out.println("Producer thread ended");
    }
}
