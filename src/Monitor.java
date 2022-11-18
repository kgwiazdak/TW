import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    Buffor buffor = new Buffor();
    ReentrantLock lock = new ReentrantLock();
    Condition restOfProducers = lock.newCondition();
    Condition restOfConsumers = lock.newCondition();
    Condition firstProducer = lock.newCondition();
    Condition firstConsumer = lock.newCondition();
    boolean isFirstProducerOccupied = false;
    boolean isFirstConsumerOccupied = false;
    long smallProducerCounter = 0;
    long bigProducerCounter = 0;
    long smallConsumerCounter = 0;
    long bigConsumerCounter = 0;

    public void consume(int numberToConsume, Type type) throws InterruptedException {
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
            if (type.equals(Type.BIG)){
                bigConsumerCounter++;
            } else {
                smallConsumerCounter++;
            }
            showStats();
            isFirstConsumerOccupied=false;
            restOfConsumers.signal();
            firstProducer.signal();
        } finally {
            lock.unlock();
        }
    }

    public void produce(int numberToProduce, Type type) throws InterruptedException {
        try {
            lock.lock();

            while (isFirstProducerOccupied) {
                restOfProducers.await();
            }
            while (buffor.getNumber()+numberToProduce>buffor.getMaximalNumber()) {
                isFirstProducerOccupied =true;
                firstProducer.await();
            }
            int currentNumber = buffor.getNumber();
            buffor.setNumber(currentNumber+numberToProduce);
            if (type.equals(Type.BIG)){
                bigProducerCounter++;
            } else {
                smallProducerCounter++;
            }
            showStats();
            isFirstProducerOccupied =false;
            restOfProducers.signal();
            firstConsumer.signal();
        } finally {
            lock.unlock();
        }
    }

    private void showStats(){
        System.out.println("smallConsumerCounter: " + smallConsumerCounter);
        System.out.println("bigConsumerCounter: " + bigConsumerCounter);
        System.out.println("smallProducerCounter: " + smallProducerCounter);
        System.out.println("bigProducerCounter: " + bigProducerCounter);
    }
}
