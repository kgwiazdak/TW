public class DecrementingThread implements Runnable {
    MyNumber myNumber;
    int n;

    public DecrementingThread(MyNumber myNumber, int n) {
        this.myNumber = myNumber;
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            myNumber.decrement();
        }
    }
}
