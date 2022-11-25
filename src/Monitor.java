import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    Buffor buffor = new Buffor();
    ReentrantLock lock = new ReentrantLock();
    Condition restOfProducers = lock.newCondition();
    Condition restOfConsumers = lock.newCondition();
    Condition firstProducer = lock.newCondition();
    Condition firstConsumer = lock.newCondition();
    long smallProducerCounter = 0;
    long bigProducerCounter = 0;
    long smallConsumerCounter = 0;
    long bigConsumerCounter = 0;

    public void consume(int numberToConsume, Type type) throws InterruptedException {
        try {
            lock.lock();
            System.out.println("Waiting in the consumers lock");
            while (lock.hasWaiters(firstConsumer)) {
                restOfConsumers.await();
                System.out.println("Waiting for rest of consumers");
            }
            while (buffor.getNumber()<numberToConsume) {
                firstConsumer.await();
                System.out.println("Waiting for first consumer");
            }
            int currentNumber = buffor.getNumber();
            buffor.setNumber(currentNumber-numberToConsume);
            if (type.equals(Type.BIG)){
                bigConsumerCounter++;
            } else {
                smallConsumerCounter++;
            }
            showStats();
            restOfConsumers.signal();
            firstProducer.signal();
        } finally {
            lock.unlock();
        }
    }

    public void produce(int numberToProduce, Type type) throws InterruptedException {
        try {
            lock.lock();
            System.out.println("Waiting in the producers lock");
            while (lock.hasWaiters(firstProducer)) {
                restOfProducers.await();
                System.out.println("Waiting for rest of producers");
            }
            while (buffor.getNumber()+numberToProduce>buffor.getMaximalNumber()) {
                firstProducer.await();
                System.out.println("Waiting for first of producest");
            }
            int currentNumber = buffor.getNumber();
            buffor.setNumber(currentNumber+numberToProduce);
            if (type.equals(Type.BIG)){
                bigProducerCounter++;
            } else {
                smallProducerCounter++;
            }
            showStats();
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
