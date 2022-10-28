import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Monitor monitor = new Monitor();
        ArrayList<? extends Thread> producentsAndConsumers = new ArrayList<>(List.of(
                new Producer(monitor, 10000),
                new Producer(monitor, 10000),
                new Consumer(monitor, 10000)
        ));
        for (Thread thread : producentsAndConsumers)
            thread.start();

        for (Thread thread : producentsAndConsumers)
            thread.join();
    }
}