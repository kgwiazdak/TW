public class Monitor {
    int number = 0;

    public int getNumber() {
        return number;
    }

    public synchronized void consume() throws InterruptedException {
        System.out.println("Consumer thread started");
        while (number==0)
            wait();
        number++;
        notify();
        System.out.println("Consumer thread ended");
    }

    public synchronized void produce() throws InterruptedException {
        System.out.println("Producer thread started");
        while (number!=0)
            wait();
        number--;
        notify();
        System.out.println("Producer thread ended");
    }
}
