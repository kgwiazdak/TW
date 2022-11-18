import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    Buffor buffor = new Buffor();
    ReentrantLock lock = new ReentrantLock();
    Condition restOfProducers = lock.newCondition();
    Condition restOfConsumers = lock.newCondition();
    Condition firstProducer = lock.newCondition();
    Condition firstConsumer = lock.newCondition();
    boolean isFirstProducentOccupied = false;
    boolean isFirstConsumerOccupied = false;

    public void consume(int numberToConsume) throws InterruptedException {
        try {
            lock.lock();
            while (isFirstConsumerOccupied) {
                restOfConsumers.await();
            }
            while (buffor.getNumber()<numberToConsume) {
                isFirstConsumerOccupied=true;
                firstConsumer.await();
            }
            int currentNumber = buffor.getNumber();
            buffor.setNumber(currentNumber-numberToConsume);
            isFirstConsumerOccupied=false;
            restOfConsumers.signal();
            firstProducer.signal();
        } finally {
            lock.unlock();
        }
    }

    public void produce(int numberToProduce) throws InterruptedException {
        try {
            lock.lock();

            while (isFirstProducentOccupied) {
                restOfProducers.await();
            }
            while (buffor.getNumber()+numberToProduce>buffor.getMaximalNumber()) {
                isFirstProducentOccupied=true;
                firstProducer.await();
            }
            int currentNumber = buffor.getNumber();
            buffor.setNumber(currentNumber+numberToProduce);
            isFirstProducentOccupied=false;
            restOfProducers.signal();
            firstConsumer.signal();
        } finally {
            lock.unlock();
        }
    }
}
