import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Monitor monitor = new Monitor();
        int smallMaximalNumber = 20;
        int bigMaximalNumber = 50;
        ArrayList<Thread> producersAndConsumers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            producersAndConsumers.add(new Consumer(monitor, Type.SMALL));
            producersAndConsumers.add(new Producer(monitor, Type.SMALL));
        }
        producersAndConsumers.add(new Producer(monitor, Type.BIG));

        for (Thread thread : producersAndConsumers)
            thread.start();

        for (Thread thread : producersAndConsumers)
            thread.join();
    }
}