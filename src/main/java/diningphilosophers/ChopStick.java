package diningphilosophers;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

public class ChopStick {

    private static int stickCount = 0;

    private boolean iAmFree = true;
    private final int myNumber;

    public ChopStick() {
        myNumber = ++stickCount;
    }

    private final Lock verrou = new ReentrantLock();

    private final Condition libre = verrou.newCondition();



    public boolean tryTake() throws InterruptedException {
        verrou.lock();
        try {
            while (!iAmFree) {
                libre.await();
            }
            iAmFree = false;
            System.out.println("Stick " + myNumber + " is taken.");
            return true;
        } finally {
            verrou.unlock();
        }
    }
        /**
        if (!iAmFree) {
            try {
                wait(timeout);
            } catch (InterruptedException ex) {
            }
            if (!iAmFree) {
                System.out.println("Stick " + myNumber + " Released after time.");
                return false;
            }
        }
        iAmFree = false;
        System.out.println("Stick " + myNumber + " Taken");
        return true;
    }
         */
/**
    synchronized public void release() {
        // assert !iAmFree;
        System.out.println("Stick " + myNumber + " Released");
        iAmFree = true;
        notifyAll(); // On prévient ceux qui attendent que la baguette soit libre
    }
 */
public void release() throws InterruptedException{
    verrou.lock();
    try {
        iAmFree = true;
        System.out.println("Stick " + myNumber + " Released");
        libre.signalAll();
    } finally {
        verrou.unlock();
    }
}


    @Override
    public String toString() {
        return "Stick#" + myNumber;
    }
}
