public class Main {
    public static void main(String[] args) throws InterruptedException {
        MyNumber myNumber = new MyNumber();
        Thread i = new Thread(new IncrementingThread(myNumber, 10000));
        Thread d = new Thread(new DecrementingThread(myNumber, 10000));
        i.start();
        d.start();
        i.join();
        d.join();
        System.out.println(myNumber.getNumber());
    }
}