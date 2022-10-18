public class MyNumber {
    int number = 0;

    public int getNumber() {
        return number;
    }

    public synchronized void decrement() {
        number--;
    }

    public synchronized void increment() {
        number++;
    }
}
